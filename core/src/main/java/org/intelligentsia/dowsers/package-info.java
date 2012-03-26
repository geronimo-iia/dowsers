package org.intelligentsia.dowsers;

/**
 * 
 * “A single model cannot be appropriate for reporting, searching, and transactional behaviors.” - Greg Young
 * 
 * <pre>
 * 					 	PRESENTATION	
 * 				/\						||
 * 				||						||
 * 			Queries					Commands
 * 				||						||
 * 				||						\/
 * 			REPORTING <--- Events --- DOMAIN
 * 
 * </pre>
 * 
 * 
 * All state changes are represented By Domain Events;
 * 
 * 
 * PACKAGE ORGANISATION:
 * <pre>
 * 				 DOMAIN		PRESENTATION	REPORTING
 * 					|\          /|\	         /|
 * 	FRAMEWORK		| \        / | \        / |		    SHARED
 * 					|  \      /  |  \      /  |
 * 					|	\    /   |   \    /   |
 * 					|    \  /    |    \  /    |
 * 					|     \/     |     \/     |
 * 					|     /\     |     /\     |
 * 					|    /  \    |    /  \    |
 *                  |   /    \   |   /    \   |
 * 				COMMANDS	   EVENTS	  QUERIES
 * </pre>
 * 
 */
