/**
 * @file    kpi_interface.h
 * @author  Aleksandr A. Lomov <lomov@cs.karelia.ru>
 * @date    10 April, 2015
 * @brief   Describe API functions to implement low-level access to smart space.
 *
 *
 * @section LICENSE
 *
 * SmartSlog is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * SmartSlog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SmartSlog KP Library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 *
 *
 * @section DESCRIPTION
 *
 * This interface is used to support a low-level API
 * to work with KPI through SmartSlog.
 * Low-level API is based on triples.
 *
 * This file is part of SmartSlog Library.
 *
 * Copyright (C) 2009 PetrSU (SmartSlog team). All rights reserved.
 *
 */

#include "kpi_api.h"

#include <stdlib.h>

#include "triplestore.h"
#include "kpi_interface.h"
#include "triple_internal.h"
#include "entity_internal.h"
#include "session_internal.h"
#include "low_api_internal.h"

#include "utils/bool.h"
#include "utils/debug.h"
#include "utils/util_func.h"
#include "utils/errors_internal.h"


#if !defined(SSLOG_EXTERN)
#if defined(SSLOG_EXPORT)
#define SSLOG_EXTERN __declspec(dllexport)
#elif defined (SSLOG_IMPORT)
#define SSLOG_EXTERN __declspec(dllimport)
#else
#define SSLOG_EXTERN
#endif
#endif


/******************************* Definitions *********************************/
/**************************** Static functions *******************************/
/**
 * @brief Convers one sparql template for storing triple in string form to triple structure.
 * @param triple_template. Triple template to create a triple
 * (@see #sslog_node_sparql_select for more information).
 * @return new triple on success or NULL otherwise (template is incorrect).
 */
static sslog_triple_t *sslog_sparql_template_to_triple(const char *triple_template);


/**
 * @brief Converts a one result to triple.
 * @param result. Results of SPARQL SELECT query.
 * @param row. Row in the result of SPARQL SELECT query.
 * @param triple_variables. Triple with elements (subject, prediacte, object) that represent
 * names of variables in the SPARQL results.
 * @return new triple with values from result based on triple_variables on success or NULL therwise.
 */
static sslog_triple_t* sslog_sparql_result_to_triple(sslog_sparql_result_t *result, sslog_sparql_result_row_t *row,
                                                     sslog_triple_t *triple_variables);
/*****************************************************************************/

/*** External functions  ***/

/**
 * @brief Executes the SSAP format insert operation.
 *
 * @param[in] ss_triple_t * first_triple. Pointer to the first triple in the triple list to be inserted.
 * @return int status. Status of the operation when completed (0 if successfull,
 *         otherwise < 0).
 */
//SSLOG_EXTERN int sslog_kpi_insert(sslog_node_t *node, sslog_triple_t *triple);

/**
 * @brief Executes the SSAP format insert graph in RDF-XML notation operation.
 *
 * @param[in] char* graph. A string that contained graph in RDF-XML notation.
 * @return int status. Status of the operation when completed (0 if successfull,
 *         otherwise < 0).
 */
//SSLOG_EXTERN int sslog_kpi_graph_insert(sslog_node_t *node, char *graph);


/**
 * @brief Executes the SSAP format update operation.
 *
 * @param[in] ss_triple_t * inserted_triples. Pointer to the first triple in the triple list to be inserted.
 * @param[in] ss_triple_t * removed_triples. Pointer to the first triple in the triple list to be removed.
 * @return int status. Status of the operation when completed (0 if successfull,
 *         otherwise -1).
 */
//SSLOG_EXTERN int sslog_kpi_update(sslog_node_t *node, ss_triple_t *inserted_triples, ss_triple_t *removed_triples);


/**
 * @brief Executes the SSAP format rdf-xml update operation.
 *
 *  Function executes SSAP UPDATE operation using rdf-xml notation.
 *
 * @param[in] ss_info_t * ss_info. A pointer to the struct holding neccessary node_id and
 *            space_id information.
 * @param[in] char * inserted_graph. A pointer to rdf-xml notation for triplets needed to be inserted.
 * @param[in] char * removed_graph. A pointer to rdf-xml notation for triplets that needed to be removed.
 * @return int status. Status of the operation when completed (0 if successfull,
 *         otherwise -1).
 */
//SSLOG_EXTERN int sslog_kpi_graph_update(sslog_node_t *node, char *inserted_graph, char *removed_graph);


/**
 * @brief Executes the SSAP format remove operation.
 *
 * @param[in] ss_triple_t * removed_triples. Pointer to the first triple in the triple list to be removed.
 * @return int status. Status of the operation when completed (0 if successfull,
 *         otherwise -1).
 */
SSLOG_EXTERN int ss_kpi_remove(ss_triple_t *removed_triples);


/**
 * @brief Executes the SSAP format remove graph in RDF-XML notation operation.
 *
 * @param[in] char* graph. A string that contained graph in RDF-XML notation.
 * @return int status. Status of the operation when completed (0 if successfull,
 *         otherwise < 0).
 */
SSLOG_EXTERN int ss_kpi_graph_remove(char * graph);


/// Queries

/**
 * @brief Executes the query operation to smart space.
 *
 * @param[in] ss_triple_t * requested_triples. Pointer to the first triple requested from the SIB.
 * @param[out] ss_triple_t * returned_triples. Pointer to the first triple returned by the SIB.
 *
 * @return int status. Status of the operation when completed (0 if successfull, otherwise -1).
 */
SSLOG_EXTERN int sslog_kpi_query(sslog_triple_t *requested_triples, sslog_triple_t **returned_triples);

/**
 * @brief  Executes the SSAP format sparql construct query operation.
 *
 * \param[in] char * query. SPARQL CONSRUCT query in text format.
 * \param[out] ss_triple_t ** returned_triples. Pointer to the first triple returned by the SIB.
 *
 * \return int status. Status of the operation when completed (0 if successfull, otherwise -1).
 */
SSLOG_EXTERN int sslog_sparql_construct_query(char *query, sslog_triple_t **returned_triples);


/**
 * @brief  Executes the SSAP format sparql ask query operation.
 *
 * @param[in] char * query. SPARQL ASK query in text format.
 * @param[out] int * result. Pointer to the answer returned by the SIB.(0 if true, 1 if false)
 *
 * @return int status. Status of the operation when completed (1 if data exists, 0 when no data and -1 otherwise).
 */
SSLOG_EXTERN int sslog_sparql_ask_query(char *query);

/**
  * @brief  Executes the SSAP format sparql select query operation.
  *
  * @param[in] char * query. SPARQL SELECT query in text format.
  * @param[out] ss_sparql_result_t * results. Pointer to results structure.
  * @param[out] int * number_of_bindings. Pointer to variable which will contain number of variables returned by SIB (if nessesary)
  *
  * @return int status. Status of the operation when completed (0 if successfull, otherwise -1).
  */
//SSLOG_EXTERN int ss_sparql_select_query(char *query, ss_sparql_result_t **results, int *number_of_bindings);




/**************************** Implementations ********************************/
/*************************** External functions ******************************/
/// @cond DOXY_EXTERNAL_API
SSLOG_EXTERN sslog_triple_t* sslog_new_triple(const char *subject, const char *predicate, const char *object,
                                              sslog_rdf_type subject_type, sslog_rdf_type object_type)
{
    sslog_triple_t *triple = sslog_new_triple_detached(subject, predicate, object, subject_type, object_type);

    if (triple == NULL) {
        sslog_error_set(NULL, sslog_error_get_last_code(), sslog_error_get_last_text());
        return NULL;
    }

    if (strncmp(triple->subject, SSLOG_TRIPLE_ANY, SSLOG_TRIPLE_ANY_LEN) == 0
            || strncmp(triple->predicate, SSLOG_TRIPLE_ANY, SSLOG_TRIPLE_ANY_LEN) == 0
            || strncmp(triple->object, SSLOG_TRIPLE_ANY, SSLOG_TRIPLE_ANY_LEN) == 0) {

        sslog_free_triple(triple);
        sslog_error_set(NULL, SSLOG_ERROR_INCORRECT_ARGUMENT,
                        SSLOG_ERROR_TEXT_INCORRECT_ARGUMENT "only detached triple can have 'ANY value' for subject/predicate/object.");

        return NULL;
    }

    list_t *triple_teplates = list_new();
    list_add_data(triple_teplates, triple);

    sslog_triple_t *old_triple = sslog_store_get_triple(sslog_session_get_default()->store, triple_teplates);

    list_free_with_nodes(triple_teplates, NULL);

    if (old_triple == NULL) {
        sslog_store_add_triple(sslog_session_get_default()->store, triple);

    } else {
        sslog_free_triple(triple);
        triple = old_triple;
    }

    sslog_error_set(NULL, SSLOG_ERROR_NO, SSLOG_ERROR_TEXT_NO);

    return triple;
}


sslog_triple_t* sslog_new_triple_detached(const char *subject, const char *predicate, const char *object, sslog_rdf_type subject_type, sslog_rdf_type object_type)
{
    sslog_error_set(NULL, SSLOG_ERROR_NO, SSLOG_ERROR_TEXT_NO);

    if (sslog_is_str_null_empty(subject) == true) {
        sslog_error_set(NULL, SSLOG_ERROR_INCORRECT_ARGUMENT,
                        SSLOG_ERROR_TEXT_INCORRECT_ARGUMENT "'subject'' is null or empty.");
        return NULL;
    }

    if (sslog_is_str_null_empty(predicate) == true) {
        sslog_error_set(NULL, SSLOG_ERROR_INCORRECT_ARGUMENT,
                        SSLOG_ERROR_TEXT_INCORRECT_ARGUMENT "'predicate' is null or empty.");
        return NULL;
    }

    if (object == NULL) {
        sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "'object'.");
        return NULL;
    }

    //sslog_triple_t *triple = sslog_alloc(sizeof(sslog_triple_t), SSLOG_FREE_FUNC sslog_free_triple);
    //sslog_triple_t *triple = sslog_kpi_new_triple(subject, predicate, object, object_type);
    sslog_internal_triple_t *int_triple = (sslog_internal_triple_t *) malloc(sizeof(sslog_internal_triple_t));

   if (int_triple == NULL) {
       sslog_error_set(NULL, SSLOG_ERROR_OUT_OF_MEMORY, SSLOG_ERROR_TEXT_OUT_OF_MEMORY);
       return NULL;
   }

   sslog_triple_t *triple = (sslog_triple_t *) &int_triple->data;

   triple->subject = sslog_strndup(subject, SSLOG_TRIPLE_SUBJECT_LEN);
   triple->predicate = sslog_strndup(predicate, SSLOG_TRIPLE_PREDICATE_LEN);
   triple->object = sslog_strndup(object, SSLOG_TRIPLE_OBJECT_LEN);
//   strncpy(triple->subject, subject, SSLOG_TRIPLE_SUBJECT_LEN);
//   strncpy(triple->predicate, predicate, SSLOG_TRIPLE_PREDICATE_LEN);
//   strncpy(triple->object, object, SSLOG_TRIPLE_OBJECT_LEN);
   //triple->subject_type = SSLOG_RDF_TYPE_URI;
   triple->subject_type = subject_type;
   triple->object_type = object_type;
   int_triple->is_stored = false;
   int_triple->linked_entity = NULL;

   return triple;
}


int sslog_node_insert_triple(sslog_node_t *node, sslog_triple_t *triple)
{
    if (node == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "node");
    }

    if (triple == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "triple");
    }

    // Create a list with triples and insert it to the smart space.
    // If the operation is successful then add triple to local store.
    list_t *triples = list_new();
    list_add_data(triples, triple);

    int result = sslog_kpi_insert_triples(node->kpi, triples);

    if (result == SSLOG_ERROR_NO) {
        sslog_error_reset(&node->last_error);
        sslog_store_add_triples(node->session->store, triples);
    } else {
        sslog_error_set(&node->last_error, result, sslog_kpi_get_error_text(result));
    }

    list_free_with_nodes(triples, NULL);

    return result;
}


int sslog_node_insert_triples(sslog_node_t *node, list_t *triples)
{
    if (node == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "node");
    }

    if (triples == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "triple");
    }

    if (list_is_empty(triples) == true) {
        SSLOG_DEBUG_FUNC("No triples to insert.");
        return sslog_error_set(&node->last_error, SSLOG_ERROR_INCORRECT_ARGUMENT,
                               SSLOG_ERROR_TEXT_INCORRECT_ARGUMENT "'triples' list is empty.");
    }

    // Inserts triple in the smart space and on success result add triples to the local store.
    int result = sslog_kpi_insert_triples(node->kpi, triples);

    if (result == SSLOG_ERROR_NO) {
        sslog_store_add_triples(node->session->store, triples);
        return sslog_error_reset(&node->last_error);
    }

    return sslog_error_set(&node->last_error, result, sslog_kpi_get_error_text(result));
}
/// @endcond
// DOXY_EXTERNAL_API



list_t* sslog_node_query_triple(sslog_node_t *node, sslog_triple_t *triple_template)
{
    if (node == NULL) {
        sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "node");
        return NULL;
    }

    if (triple_template == NULL) {
        sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "triple");
        return NULL;
    }

    list_t *triples_templates = list_new();
    list_add_data(triples_templates, triple_template);

    list_t *query_triples = NULL;

    int result = sslog_kpi_query_triples(node->kpi, triples_templates, &query_triples);

    list_free_with_nodes(triples_templates, NULL);

    if (result == SSLOG_ERROR_NO) {
        sslog_error_reset(&node->last_error);
    } else {
        sslog_error_set(&node->last_error, result, sslog_kpi_get_error_text(result));
        sslog_free_triples(query_triples);
        return NULL;
    }

    if (query_triples == NULL) {
        return list_new();
    }

    //TODO: make function add/get triples.
    sslog_store_add_triples(node->session->store, query_triples);
    list_t *stored_triples = sslog_store_query_triples(node->session->store, query_triples);

    sslog_free_triples(query_triples);

    return stored_triples;
}

list_t *sslog_node_query_triples(sslog_node_t *node, list_t *triple_templates)
{
    if (node == NULL) {
        sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "node");
        return NULL;
    }

    if (triple_templates == NULL) {
        sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "triples");
        return NULL;
    }

    if (list_is_empty(triple_templates) == true) {
        sslog_error_set(&node->last_error, SSLOG_ERROR_NO, SSLOG_ERROR_TEXT_NO);
        return NULL;
    }

    list_t *query_triples = NULL;

    int result = sslog_kpi_query_triples(node->kpi, triple_templates, &query_triples);

    if (result == SSLOG_ERROR_NO) {
        sslog_error_reset(&node->last_error);
    } else {
        sslog_error_set(&node->last_error, result, sslog_kpi_get_error_text(result));
    }

    if (query_triples == NULL) {
        return list_new();
    }

    //TODO: make function add/get triples.
    sslog_store_add_triples(node->session->store, query_triples);
    list_t *stored_triples = sslog_store_query_triples(node->session->store, query_triples);

    sslog_free_triples(query_triples);

    return stored_triples;
}


int sslog_node_remove_triple(sslog_node_t *node, sslog_triple_t *triple_template)
{
    if (node == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "node");
    }

    if (triple_template == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "triple_template");
    }

    list_t *triples_templates = list_new();
    list_add_data(triples_templates, triple_template);

    int result = sslog_kpi_remove_triples(node->kpi, triples_templates);


    if (result == SSLOG_ERROR_NO) {
        sslog_error_reset(&node->last_error);
        sslog_remove_triples(triples_templates);
    } else {
        sslog_error_set(&node->last_error, result, sslog_kpi_get_error_text(result));
    }

    list_free_with_nodes(triples_templates, NULL);

    return result;
}


int sslog_node_remove_triples(sslog_node_t *node, list_t *triples_templates)
{
    if (node == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, "node");
    }

    if (triples_templates == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, "triple");
    }

    if (list_is_empty(triples_templates) == true) {
        return sslog_error_set(&node->last_error, SSLOG_ERROR_NO, SSLOG_ERROR_TEXT_NO);
    }

    int result = sslog_kpi_remove_triples(node->kpi, triples_templates);

    if (result == SSLOG_ERROR_NO) {
        sslog_error_reset(&node->last_error);
        sslog_remove_triples(triples_templates);
    } else {
        sslog_error_set(&node->last_error, result, sslog_kpi_get_error_text(result));
    }

    return result;
}


void sslog_free_triple(sslog_triple_t *triple)
{
    if (triple == NULL) {
        return;
    }

    if (sslog_triple_is_stored(triple) == true) {
        return;
    }


    sslog_free_entity(sslog_triple_as_internal(triple)->linked_entity);

    sslog_triple_as_internal(triple)->linked_entity = NULL;
    sslog_triple_as_internal(triple)->is_stored = false;

    free(triple->subject);
    free(triple->predicate);
    free(triple->object);

    triple->subject_type = SSLOG_RDF_TYPE_INCORRECT;
    triple->object_type = SSLOG_RDF_TYPE_INCORRECT;

    free(triple);
}


int sslog_node_update_triple(sslog_node_t *node, sslog_triple_t *current_triple, sslog_triple_t *new_triple)
{
    if (node == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, "node");
    }

    if (current_triple == NULL && new_triple == NULL) {
        return sslog_error_reset(&node->last_error);
    }

    list_t *current_triples = list_new();
    list_t *new_triples = list_new();

    if (current_triple != NULL) {
        list_add_data(current_triples, current_triple);
    }

    if (new_triple != NULL) {
        list_add_data(new_triples, new_triple);
    }

    int result = sslog_kpi_update_triples(node->kpi, current_triples, new_triples);

    if (result != SSLOG_ERROR_NO) {
        sslog_error_reset(&node->last_error);
        sslog_store_update_triples(node->session->store, current_triples, new_triples);
    } else {
        sslog_error_set(&node->last_error, result, sslog_kpi_get_error_text(result));
    }

    list_free_with_nodes(current_triples, NULL);
    list_free_with_nodes(new_triples, NULL);

    return result;
}


int sslog_node_update_triples(sslog_node_t *node, list_t *current_triples, list_t *new_triples)
{
    if (node == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, "node");
    }

    if (current_triples == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, "current_triples");
    }

    if (new_triples == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, "new_triples");
    }

    if (list_is_empty(current_triples) == true
            && list_is_empty(new_triples) == true) {
        return sslog_error_reset(&node->last_error);
    }

    int result = sslog_kpi_update_triples(node->kpi, current_triples, new_triples);

    if (result != SSLOG_ERROR_NO) {
        sslog_error_reset(&node->last_error);
        sslog_store_update_triples(node->session->store, current_triples, new_triples);
    } else {
        sslog_error_set(&node->last_error, result, sslog_kpi_get_error_text(result));
    }

    return result;
}


int sslog_insert_triple(sslog_triple_t *triple)
{
    if (triple == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "triple");
    }

    // SmartSlog does not store triple-templates in the store.
    // A triple-template is regular triple where elements (subject, predicate, object)
    // can be a mask (any value).
    if (sslog_triple_is_template(triple) == true) {
        return sslog_error_set(NULL, SSLOG_ERROR_INCORRECT_ARGUMENT,
                               SSLOG_ERROR_TEXT_INCORRECT_ARGUMENT "triple is triple-template");
    }

    int result = sslog_store_add_triple(sslog_session_get_default()->store, triple);

    if (result == SSLOG_ERROR_NO) {
        return sslog_error_reset(NULL);
    }

    return sslog_error_set(NULL, result, "Triple cannot be inserted.");
}


int sslog_insert_triples(list_t *triples)
{
    if (triples == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "triples");
    }

    if (list_is_empty(triples) == true) {
        return sslog_error_reset(NULL);
    }

    sslog_store_add_triples(sslog_session_get_default()->store, triples);

    return sslog_error_reset(NULL);
}





int sslog_remove_triples(list_t *triple_templates)
{
    if (triple_templates == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "triple_templates");
    }

    if (list_is_empty(triple_templates) == true) {
        return sslog_error_reset(NULL);
    }

    sslog_store_remove_triples(sslog_session_get_default()->store, triple_templates);

    return sslog_error_reset(NULL);
}


int sslog_remove_triple(sslog_triple_t *triple_template)
{
    if (triple_template == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "triple_template");
    }

    list_t *triples_templates = list_new();
    list_add_data(triples_templates, triple_template);

    sslog_store_remove_triples(sslog_session_get_default()->store, triples_templates);

    list_free_with_nodes(triples_templates, NULL);

    return sslog_error_reset(NULL);
}


list_t *sslog_query_triples(list_t *triples_templates)
{
    if (triples_templates == NULL) {
        sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "triple_templates");
        return  NULL;
    }

    if (list_is_empty(triples_templates) == true) {
        sslog_error_reset(NULL);
        return  NULL;
    }

    sslog_error_reset(NULL);

    return sslog_store_query_triples(sslog_session_get_default()->store, triples_templates);
}


list_t *sslog_query_triple(sslog_triple_t *triple_template)
{
    if (triple_template == NULL) {
        sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "triple_template");
        return  NULL;
    }

    list_t *triples_templates = list_new();
    list_add_data(triples_templates, triple_template);

    list_t *query_triples = sslog_store_query_triples(sslog_session_get_default()->store, triples_templates);

    list_free_with_nodes(triples_templates, NULL);

    sslog_error_reset(NULL);

    return query_triples;
}


int sslog_update_triples(list_t *current_triples, list_t *new_triples)
{
    if (current_triples == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, "current_triples");
    }

    if (new_triples == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, "new_triples");
    }

    //TODO: return bool or not
    if (list_is_empty(current_triples) == 1
            && list_is_empty(new_triples) == 1) {
        return sslog_error_reset(NULL);
    }

    sslog_store_update_triples(sslog_session_get_default()->store, current_triples, new_triples);

    return sslog_error_reset(NULL);
}


int sslog_update_triple(sslog_triple_t *current_triple, sslog_triple_t *new_triple)
{
    if (current_triple == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, "current_triple");
    }

    if (new_triple == NULL) {
        return sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, "new_triple");
    }

    list_t * current_triples = list_new();
    list_t * new_triples = list_new();

    list_add_data(current_triples, current_triple);
    list_add_data(new_triples, new_triple);

    sslog_store_update_triples(sslog_session_get_default()->store, current_triples, new_triples);

    list_free_with_nodes(current_triples, NULL);
    list_free_with_nodes(new_triples, NULL);

    return sslog_error_reset(NULL);
}


list_t *sslog_node_subscribe_triples(sslog_node_t *node, list_t *triples_templates)
{
    if (node == NULL) {
        sslog_error_set(NULL, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "node");
        return NULL;
    }

    if (list_is_null_or_empty(triples_templates) == true) {
        sslog_error_set(NULL, SSLOG_ERROR_INCORRECT_ARGUMENT,
                        SSLOG_ERROR_TEXT_INCORRECT_ARGUMENT "'triples_templates' is null or empty");
        return NULL;
    }

    list_t *query_triples = NULL;

    int result = 0;//sslog_kpi_subscribe_triples(node->kpi, triples_templates, &query_triples);

    list_free_with_nodes(triples_templates, NULL);

    if (result == SSLOG_ERROR_NO) {
        sslog_error_reset(&node->last_error);
    } else {
        sslog_error_set(&node->last_error, result, sslog_kpi_get_error_text(result));
    }

    if (query_triples != NULL) {
        sslog_store_add_triples(node->session->store, query_triples);
    }

    return query_triples;
}


list_t *sslog_node_subscribe_triple(sslog_node_t *node, sslog_triple_t *triple_template)
{
    if (node == NULL) {
        sslog_error_set(&node->last_error, SSLOG_ERROR_NULL_ARGUMENT,
                        SSLOG_ERROR_TEXT_NULL_ARGUMENT "node");
        return NULL;
    }

    if (triple_template == NULL) {
        sslog_error_set(&node->last_error, SSLOG_ERROR_NULL_ARGUMENT,
                        SSLOG_ERROR_TEXT_NULL_ARGUMENT "triple_template");
        return NULL;
    }

    list_t *triples = list_new();
    list_add_data(triples, triple_template);

    list_t *query_triples = sslog_node_subscribe_triples(node, triples);

    list_free_with_nodes(triples, NULL);

    return query_triples;
}




/******************** Node functions for SPARQL queries **********************/
sslog_sparql_result_t* sslog_node_sparql_enpoint_select(sslog_node_t *node, const char *endpoint_address, const char *query, const char *extra_parameters, const char *triples_template)
{
    if (node == NULL) {
        sslog_error_set(&node->last_error, SSLOG_ERROR_NULL_ARGUMENT, SSLOG_ERROR_TEXT_NULL_ARGUMENT "node.");
        return NULL;
    }

    if (sslog_is_str_null_empty(endpoint_address) == true) {
        sslog_error_set(&node->last_error, SSLOG_ERROR_INCORRECT_ARGUMENT, SSLOG_ERROR_TEXT_INCORRECT_ARGUMENT "'endpoint_address' is NULL or empty.");
        return NULL;
    }

    if (sslog_is_str_null_empty(query) == true) {
        sslog_error_set(&node->last_error, SSLOG_ERROR_INCORRECT_ARGUMENT, SSLOG_ERROR_TEXT_INCORRECT_ARGUMENT "'query' is NULL or empty.");
        return NULL;
    }

    sslog_sparql_result_t *sparql_result = NULL;

    int result = SSLOG_ERROR_NO;

    if (sslog_is_str_null_empty(extra_parameters) == true) {
        result = sslog_kpi_sparql_endpoint_select(endpoint_address, query, "", &sparql_result);
    } else {
        result = sslog_kpi_sparql_endpoint_select(endpoint_address, query, extra_parameters, &sparql_result);
    }

    if (result != SSLOG_ERROR_NO) {
        sslog_error_set(&node->last_error, result, sslog_kpi_get_error_text(result));
        return NULL;
    }

    if (sslog_is_str_null_empty(triples_template) == true) {
        sslog_error_reset(&node->last_error);
        return sparql_result;
    }

    list_t *variable_triples = sslog_sparql_template_to_triples(triples_template);

    sslog_store_sparql_results(sparql_result, variable_triples);

    sslog_free_triples(variable_triples);

    sslog_error_reset(&node->last_error);

    return sparql_result;
}


bool sslog_node_sparql_ask(sslog_node_t *node, const char *query)
{
    if (node == NULL) {
        sslog_error_set(&node->last_error, SSLOG_ERROR_NULL_ARGUMENT,
                        SSLOG_ERROR_TEXT_NULL_ARGUMENT "node.");
        return false;
    }

    if (sslog_is_str_null_empty(query) == true) {
        sslog_error_set(&node->last_error, SSLOG_ERROR_INCORRECT_ARGUMENT,
                        SSLOG_ERROR_TEXT_INCORRECT_ARGUMENT "'query' is NULL or empty.");
        return false;
    }

    int query_result = -1;
    int result = sslog_kpi_sparql_ask_query(node->kpi, query, &query_result);

    if (result == SSLOG_ERROR_NO) {
        sslog_error_reset(&node->last_error);
    } else {
        sslog_error_set(&node->last_error, result, sslog_kpi_get_error_text(result));
        return false;
    }

    return (query_result == 1) ? true : false;
}


sslog_sparql_result_t* sslog_node_sparql_select(sslog_node_t *node, const char *query, const char *triples_template)
{
    if (node == NULL) {
        sslog_error_set(&node->last_error, SSLOG_ERROR_NULL_ARGUMENT,SSLOG_ERROR_TEXT_NULL_ARGUMENT "node.");
        return NULL;
    }

    if (sslog_is_str_null_empty(query) == true) {
        sslog_error_set(&node->last_error, SSLOG_ERROR_INCORRECT_ARGUMENT, SSLOG_ERROR_TEXT_INCORRECT_ARGUMENT "'query' is NULL or empty.");
        return NULL;
    }

    sslog_sparql_result_t *sparql_result = NULL;

    int result = sslog_kpi_sparql_select(node->kpi, query, &sparql_result);

    if (result != SSLOG_ERROR_NO) {
        sslog_error_set(&node->last_error, result, sslog_kpi_get_error_text(result));
        return NULL;
    }

    if (sslog_is_str_null_empty(triples_template) == true) {
        sslog_error_reset(&node->last_error);
        return sparql_result;
    }

    list_t *variable_triples = sslog_sparql_template_to_triples(triples_template);


    sslog_store_sparql_results(sparql_result, variable_triples);

    sslog_free_triples(variable_triples);

    sslog_error_reset(&node->last_error);

    return sparql_result;
}


list_t* sslog_node_sparql_construct(sslog_node_t *node, const char *query)
{
    if (node == NULL) {
        sslog_error_set(&node->last_error, SSLOG_ERROR_NULL_ARGUMENT,
                        SSLOG_ERROR_TEXT_NULL_ARGUMENT "node.");
        return NULL;
    }

    if (sslog_is_str_null_empty(query) == true) {
        sslog_error_set(&node->last_error, SSLOG_ERROR_INCORRECT_ARGUMENT,
                        SSLOG_ERROR_TEXT_INCORRECT_ARGUMENT "'query' is NULL or empty.");
        return NULL;
    }

    list_t *constructed_triples = NULL;
    int result = sslog_kpi_sparql_construct(node->kpi, query, &constructed_triples);

    if (result != SSLOG_ERROR_NO) {
        sslog_error_set(&node->last_error, result, sslog_kpi_get_error_text(result));
        return NULL;
    }

    // Return empty list if there are no information in the smart space to construct triples.
    if (constructed_triples == NULL) {
        return list_new();
    }

    //TODO: make function add/get triples.
    sslog_store_add_triples(node->session->store, constructed_triples);
    list_t *stored_triples = sslog_store_query_triples(node->session->store, constructed_triples);

    sslog_free_triples(constructed_triples);

    return stored_triples;
}
/*****************************************************************************/


/****************************** Implementation *******************************/
/**************************** Internal functions *****************************/
sslog_sparql_result_t *sslog_new_sparql_result(const char **names, int number_of_bindings, int rows_count)
{
    sslog_sparql_result_t *result = (sslog_sparql_result_t *) malloc(sizeof(sslog_sparql_result_t));

    if (result == NULL) {
        return NULL;
    }

    if (number_of_bindings <= 0) {
        number_of_bindings = 0;
    }

    if (rows_count < 0) {
        rows_count = 0;
    }

    // +1 for last NULL element.
    //result->type = (int *) calloc(number_of_bindings + 1, sizeof(int));
    result->names = (char **) calloc(number_of_bindings + 1, sizeof(char *));
    //result->value = (char **) calloc(number_of_bindings + 1, sizeof(char *));

    result->bindings_count = number_of_bindings;
    result->rows_count = rows_count;

    if (rows_count > 0) {
        result->rows = (sslog_sparql_result_row_t **) calloc(rows_count + 1, sizeof(sslog_sparql_result_row_t *));
    } else {
        result->rows = NULL;
    }
    //INIT_LIST_HEAD(&result->rows.links);

    if (names != NULL) {
        for (int i = 0; i < number_of_bindings; ++i) {
            result->names[i] = sslog_strndup(names[i], SSLOG_TRIPLE_URI_LEN);
        }
    }

    return result;
}


sslog_sparql_result_row_t *sslog_new_sparql_result_row(int number_of_bindings)
{
    sslog_sparql_result_row_t *row = (sslog_sparql_result_row_t *) malloc(sizeof(sslog_sparql_result_row_t));

    if (row == NULL) {
        return NULL;
    }

    row->types = (int *) calloc(number_of_bindings + 1, sizeof(int));
    row->values = (char **) calloc(number_of_bindings + 1, sizeof(char *));

    return row;
}


void sslog_free_sparql_result(sslog_sparql_result_t *result)
{
    if (result == NULL) {
        return;
    }

//    list_head_t *list_walker = NULL;
//    list_for_each(list_walker, &result->rows.links) {
//        list_t *node = list_entry(list_walker, list_t, links);

    for (int row_index = 0; row_index < result->rows_count; ++row_index) {

        //sslog_sparql_result_row_t *row = (sslog_sparql_result_row_t *) node->data;
        sslog_sparql_result_row_t *row = result->rows[row_index];

        free(row->types);

        for (int i = 0; i < result->bindings_count; ++i) {
            free(row->values[i]);
        }

        free(row->values);
        free(row);
    }

    for (int i = 0; i < result->bindings_count; ++i) {
        free(result->names[i]);
    }

    free(result->rows);
    free(result->names);
    //list_del_and_free_nodes(&result->rows, NULL);

    free (result);
}


/****************************** Implementation *******************************/
/**************************** Internal functions *****************************/
list_t *sslog_sparql_template_to_triples(const char *triples_templates)
{
    const char delim[] = ".";
    char *token = NULL;

    char* string = sslog_strndup(triples_templates, 1000);
    char* tofree = string;

    list_t *substrings = list_new();
    list_t *triples = list_new();

    token = strtok(string, delim);

    while (token != NULL) {
        list_add_data(substrings, token);
        token = strtok(NULL, delim);
    }

    list_head_t *list_walker = NULL;
    list_for_each(list_walker, &substrings->links) {
        list_t *node = list_entry(list_walker, list_t, links);
        char *substring = (char *) node->data;

        sslog_triple_t *triple = sslog_sparql_template_to_triple(substring);

        if (triple != NULL) {
            list_add_data(triples, triple);
        }

    }

    free(tofree);
    list_free_with_nodes(substrings, NULL);

    return triples;
}


int sslog_store_sparql_results(sslog_sparql_result_t *result, list_t *triples_variables)
{
    int stored_triples_counter = 0;

//    list_head_t *list_walker_results = NULL;
//    list_for_each(list_walker_results, &result->rows.links) {
//        list_t *node = list_entry(list_walker_results, list_t, links);
//        sslog_sparql_result_row_t *row = (sslog_sparql_result_row_t *) node->data;

    for (int i = 0; i < result->rows_count; ++i) {

        sslog_sparql_result_row_t *row = result->rows[i];

        list_head_t *list_walker = NULL;
        list_for_each(list_walker, &triples_variables->links) {
            list_t *node = list_entry(list_walker, list_t, links);
            sslog_triple_t *triple_variables = (sslog_triple_t *) node->data;

            sslog_triple_t *triple = sslog_sparql_result_to_triple(result, row, triple_variables);

            if (triple != NULL) {
                sslog_store_add_triple(sslog_session_get_default()->store, triple);
                sslog_free_triple(triple);
                ++stored_triples_counter;
            }
        }
    } // list_for_each resuts

    return stored_triples_counter;
}


list_t* sslog_sparql_result_to_triples(sslog_sparql_result_t *result, list_t *triples_variables)
{
    if (triples_variables == NULL) {
        return NULL;
    }

    list_t *triples = list_new();

    for (int i = 0; i < result->rows_count; ++i) {

        sslog_sparql_result_row_t *row = result->rows[i];

        list_head_t *list_walker = NULL;
        list_for_each(list_walker, &triples_variables->links) {
            list_t *node = list_entry(list_walker, list_t, links);
            sslog_triple_t *triple_variables = (sslog_triple_t *) node->data;

            sslog_triple_t *triple = sslog_sparql_result_to_triple(result, row, triple_variables);

            if (triple != NULL) {
                list_add_data(triples, triple);
            }
        }
    } // list_for_each resuts

    return triples;
}
/*****************************************************************************/



/****************************** Implementation *******************************/
/***************************** Static functions ******************************/
static sslog_triple_t *sslog_sparql_template_to_triple(const char *triple_template)
{
    const char delim[] = {' ', '\0'};
    char *token = NULL;

    char* string = sslog_strndup(triple_template, 1000);
    char* tofree = string;

    int counter = 0;

    char *subject = NULL;
    char *predicate = NULL;
    char *object = NULL;

    sslog_triple_t *triple = NULL;

    token = strtok(string, delim);
    while (token != NULL) {

        switch(counter) {
        case 0: // subject
            subject= token;
            ++counter;
            break;
        case 1: // predicate
            predicate = token;
            ++counter;
            break;
        case 2: // object
            object = token;
            ++counter;
            break;
        default:
            break;
        }

        // Then all data found, then cretion triple.
        if (counter == 3) {
            triple = sslog_new_triple_detached(subject, predicate, object, SSLOG_RDF_TYPE_URI, SSLOG_RDF_TYPE_LIT);
            break;
        }

        token = strtok(NULL, delim);
    }

    free(tofree);

    return triple;
}







static sslog_triple_t* sslog_sparql_result_to_triple(sslog_sparql_result_t *result, sslog_sparql_result_row_t *row, sslog_triple_t *triple_variables)
{
    sslog_triple_t *triple = sslog_new_triple_empty();

    for (int i = 0; i < result->bindings_count; ++i) {

        if (row->types[i] == SSLOG_RDF_TYPE_UNBOUND) {
            break;
        }

        if (strncmp(result->names[i], triple_variables->subject, SSLOG_TRIPLE_URI_LEN) == 0) {
            triple->subject = sslog_strndup(row->values[i], SSLOG_TRIPLE_URI_LEN);
            triple->subject_type = row->types[i];
        } else if (strncmp(result->names[i], triple_variables->predicate, SSLOG_TRIPLE_URI_LEN) == 0) {
            triple->predicate = sslog_strndup(row->values[i], SSLOG_TRIPLE_URI_LEN);
        } else if (strncmp(result->names[i], triple_variables->object, SSLOG_TRIPLE_URI_LEN) == 0) {
            triple->object = sslog_strndup(row->values[i], SSLOG_TRIPLE_OBJECT_LEN);
            triple->object_type = row->types[i];
        }
    }

    if (triple->subject == NULL || triple->predicate == NULL || triple->object == NULL) {
        sslog_free_triple(triple);
        return NULL;
    }

    return triple;
}
