/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2014-2016 ForgeRock AS.
 */

import groovyx.net.http.RESTClient
import org.apache.http.client.HttpClient
import org.forgerock.openicf.connectors.scriptedrest.ScriptedRESTConfiguration
import org.forgerock.openicf.misc.scriptedcommon.OperationType
import org.identityconnectors.common.logging.Log
import org.identityconnectors.framework.common.objects.Attribute
import org.identityconnectors.framework.common.objects.AttributeUtil
import org.identityconnectors.framework.common.objects.Name
import org.identityconnectors.framework.common.objects.ObjectClass
import org.identityconnectors.framework.common.objects.OperationOptions
import org.identityconnectors.framework.common.objects.SearchResult
import org.identityconnectors.framework.common.objects.Uid
import org.identityconnectors.framework.common.objects.filter.Filter

import groovy.json.JsonSlurper
import static groovyx.net.http.Method.GET

// imports used for CREST based REST APIs
import org.forgerock.openicf.misc.crest.CRESTFilterVisitor
import org.forgerock.openicf.misc.crest.VisitorParameter

def operation = operation as OperationType
def configuration = configuration as ScriptedRESTConfiguration
def httpClient = connection as HttpClient
def connection = customizedConnection as RESTClient
def filter = filter as Filter
def log = log as Log
def objectClass = objectClass as ObjectClass
def options = options as OperationOptions
def resultHandler = handler

log.info("Entering " + operation + " Script")


// def email_address = null
// if (filter != null) {
//     email_address = filter.accept(CRESTFilterVisitor.VISITOR, [
//             translateName: { String name ->
//                 if (AttributeUtil.namesEqual(name, Uid.NAME)) {
//                     log.info("-->attribute to search is: " + name)
//                     return "work_email"
//                 } else if (AttributeUtil.namesEqual(name, Name.NAME)) {
//                     log.info("-->attribute to search is: " + name)
//                     return "work_email"
//                 } else if (AttributeUtil.namesEqual(name, "managerEmail")) {
//                     log.info("-->attribute to search is: " + name)
//                     return "contactInformation/manager_email"
//                 } else if (AttributeUtil.namesEqual(name, "emailAddress")) {
//                     log.info("-->attribute to search is: " + name)
//                     return "contactInformation/work_email"
//                 } else if (AttributeUtil.namesEqual(name, "familyName")) {
//                     log.info("-->attribute to search is: " + name)
//                     return "name/legal_last_name"
//                 } else if (AttributeUtil.namesEqual(name, "givenName")) {
//                     log.info("-->attribute to search is: " + name)
//                     return "name/legal_first_name"
//                 } else if (AttributeUtil.namesEqual(name, "displayName")) {
//                     log.info("-->attribute to search is: " + name)
//                     return "preferred_first_name"
//                 } else if (AttributeUtil.namesEqual(name, "vpEmail")) {
//                     log.info("-->attribute to search is: " + name)
//                     return "contactInformation/vp_email"
//                 } else {
//                     throw new IllegalArgumentException("Unknown field name: ${name}");
//                 }
//             },
//             convertValue : { Attribute value ->
//                     log.info("-->the string converted value is: " + AttributeUtil.getStringValue(value))
//                     return AttributeUtil.getStringValue(value)
//             }] as VisitorParameter).toString();
// }
switch (objectClass) {
    case ObjectClass.ACCOUNT:
        def searchResult = connection.request(GET) { req ->
            uri.path = '/test725/employee/employees'
            // uri.query = [
            //         null
            // ]
            headers.'API-TOKEN' = 'ed617a2ab8576be024f61f41f7d7d9310dcbda69d599f34142d90fa2fae1ac68'
            response.success = { resp, json ->
                json.data.each() { value ->
                    // log.info("-->value for a user is: " + json.data.work_email)
                    // log.info("-->user entry received: ")
                    resultHandler {
                        uid value.employee_id
                        id value.employee_id
                        attribute 'displayName', value?.preferred_first_name
                        attribute 'givenName', value?.legal_first_name
                        attribute 'familyName', value?.legal_last_name
                        attribute 'emailAddress', value?.work_email
                        attribute 'role', value?.business_title
                        attribute 'managerId', value?.manager_id
                        attribute 'managerEmail', value?.manager_email
                        attribute 'vpEmail', value?.vp_email
                        attribute 'costCenterName', value?.cost_center_name
                        attribute 'costCenterId', value?.cost_center_id
                        attribute 'location', value?.location_name
                    }
                }
                json
            }
        }

        // return new SearchResult()
        return new SearchResult(null, -1)

}
