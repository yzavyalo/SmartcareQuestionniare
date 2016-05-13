
/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#include <stdlib.h>
#include <string.h>
#include <jni.h>
#include "ontology/smartcare.h"
#include <android/log.h>
#include <errno.h>
#include "agent.h"

#include "handlers.h"
#include "jni_utils.h"
#include "globals.h"

#define TAG "SS"
#define KP_NS_URI "http://oss.fruct.org/smartcare#"
#define KP_PREDICATE KP_NS_URI"sendAlarm"

/*
 *   Подключаемся к Интелектуальному пространству
 *  SmartSpace node initialization by hostname_, ip_,port
 */
JNIEXPORT jlong JNICALL Java_com_petrsu_cardiacare_smartcare_SmartCare_connectSmartSpace( JNIEnv* env,
                                                  jobject thiz , jstring hostname, jstring ip, jint port) {

    const char *hostname_ = (*env)->GetStringUTFChars(env, hostname, NULL);
    if( hostname_ == NULL) {
        return -1;
    }
    const char *ip_ = (*env)->GetStringUTFChars(env, ip, NULL);
    if( ip_ == NULL ){
        return -1;
    }
    long node = kp_connect_smartspace( hostname_, ip_, port);

    if (node == -1) {
        __android_log_print(ANDROID_LOG_INFO, TAG, "Can't join to SS");
        return -1;
    } else {
        __android_log_print(ANDROID_LOG_INFO, TAG, "KP joins to SS.");
    }
    /*
     * Initialize global references to java classes
     * to use'em in handlers etc.
     */
    if( -1 == init_global_instances(env, thiz) ) {
        return -1;
    }
    /*
     * Get Java Virtual Machine Instance
     * to use it in callbacks generally
     */
    if( -1 == init_JVM_instance(env) ){
        return -1;
    }
    /*
     * Return node descriptor
     * to use it further in subscriptions etc.
     */
    return (jlong) node;

}
/*
 *  Disconnect from smartspace
 *
 */
JNIEXPORT void JNICALL Java_com_petrsu_cardiacare_smartcare_SmartCare_disconnectSmartSpace( JNIEnv* env,
                                                  jobject thiz , jlong nodeDescriptor){
    int result = kp_disconnect_smartspace(nodeDescriptor);

    if (result == -1)
        __android_log_print(ANDROID_LOG_INFO, TAG, "Node Error");
    else
        __android_log_print(ANDROID_LOG_INFO, TAG, "KP leaves SS...");
}


JNIEXPORT jobject JNICALL Java_com_petrsu_cardiacare_smartcare_SmartCare_getQuestionnaire
        (JNIEnv* env, jobject thiz , jlong nodeDescriptor){

    sslog_node_t *node = (sslog_node_t *) nodeDescriptor;
    if (node == NULL ){
        __android_log_print(ANDROID_LOG_INFO, TAG, "Node Error");
        return NULL;
    }
    char* questionnaire_uri;
    sslog_individual_t *questionnaire_ss =  kp_get_questionnaire(node, &questionnaire_uri);

    /**************/
    jobject *questionnaire;
    questionnaire = (*env)->NewObject(env, class_questionnaire, questionnaire_constructor, (*env)->NewStringUTF(env, questionnaire_uri) );

    char* question_uri;
    char* next_question_uri;

    jobject first_question;
    first_question = kp_get_first_question(env, questionnaire, node, questionnaire_ss, &next_question_uri);
    question_uri = next_question_uri;
    (*env)->CallVoidMethod(env, questionnaire, add_question, first_question);

    jobject next_question;
    while (next_question_uri != NULL) {
        next_question = kp_get_next_question(env, questionnaire, node, questionnaire_ss, question_uri, &next_question_uri);
        (*env)->CallVoidMethod(env, questionnaire, add_question, next_question);
        question_uri = next_question_uri;
    }
    /**************/
    return questionnaire;
}