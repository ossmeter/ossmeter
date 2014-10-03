package org.ossmeter.platform.visualisation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class Chart {
	
	protected String chartName;
	protected List<String> requiredFields;
	protected List<String> optionalFields;
	
	// TODO: Validate the specification. 
	public Chart(JsonNode chartDefinition) {
		requiredFields = new ArrayList<>();
		optionalFields = new ArrayList<>();
		
		chartName = chartDefinition.path("name").textValue();
		
		ArrayNode fields = (ArrayNode) chartDefinition.get("fields");
		for (JsonNode f : fields) {
			String name = f.get("name").textValue();
			// TODO: the field type, for type checking the data
			boolean required = false; 
			if (f.has("required")){
				required = f.path("required").asBoolean(true);
			}
			if (required) {
				this.requiredFields.add(name);
			} else {
				this.optionalFields.add(name);
			}
		}
	}
	/*
{
	"metricid" : "akjsjadgi",
	"description" : "",
	"type" : "line",
	"datatable" : [],
	"x" : "Date",
	"y" : "Commits"

}
	 */

	protected ArrayNode createDatatable(JsonNode datatableSpec, DBCollection collection, DBObject query) {
		String rowName = null;
		if (datatableSpec.has("rows")) {
			// TODO: May need more checking here if rows can be more complex
			rowName = datatableSpec.path("rows").textValue();
			rowName = rowName.replace("$", ""); 
		}
		ArrayNode colNames = (ArrayNode) datatableSpec.path("cols");
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode results = mapper.createArrayNode();
		
		if (rowName != null){
			Iterator<DBObject> it = collection.find(query).iterator();
			while(it.hasNext()) {
				DBObject dbobj = it.next();
				BasicDBList rows = (BasicDBList)dbobj.get(rowName);
				
				Iterator<Object> rowsIt = rows.iterator();
				while (rowsIt.hasNext()) {
					BasicDBObject row = (BasicDBObject) rowsIt.next();
					ObjectNode r = mapper.createObjectNode();

					for (int i = 0; i < colNames.size(); i++) {
						JsonNode col = colNames.get(i);
						String name  = col.get("name").asText();
						String field = col.get("field").asText();
						
						field = field.replace("$", "");
						Object value = null;
						if (field.equals("__date")) {
							value = dbobj.get(field);
						} else {
							value = row.get(field);
						}
						
						r.put(name, mapper.valueToTree(value));
					}
					results.add(r);
				}
			}
		} else {
			Iterator<DBObject> it = collection.find(query).iterator();
			while(it.hasNext()) {
				DBObject dbobj = it.next();
				ObjectNode r = mapper.createObjectNode();
				for (int i = 0; i < colNames.size(); i++) {
					JsonNode col = colNames.get(i);
					String name  = col.get("name").asText();
					String field = col.get("field").asText();
					
					field = field.replace("$", "");
					Object value = null;
					value = dbobj.get(field);
					mapper.valueToTree(value);
					r.put(name, mapper.valueToTree(value));
				}
				results.add(r);
			}
		}
		return results;
	}

	public void completeFields(ObjectNode visualisation, JsonNode vis) {
		for (String field : requiredFields) {
			visualisation.put(field, vis.path(field).textValue());
		}
		for (String field : optionalFields) {
			if (vis.has(field)) {
				visualisation.put(field, vis.path(field).textValue());
			}
		}
	}
}
