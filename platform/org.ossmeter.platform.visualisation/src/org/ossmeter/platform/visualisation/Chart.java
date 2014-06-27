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
	protected List<String> fields;
	
	public Chart(JsonNode chartDefinition) {
		fields = new ArrayList<>();
		chartName = chartDefinition.path("name").textValue();
		ArrayNode fields = (ArrayNode) chartDefinition.get("fields");
		for (JsonNode f : fields) {
			String name = f.get("name").textValue();
			// TODO: the field type, for type checking the data
			boolean required = false; // TODO use this.
			if (f.has("required")){
				required = f.path("required").asBoolean(true);
			}
			this.fields.add(name);
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
	// TODO: How do we deal with multiple visualisations? 
	protected void visualise(JsonNode specification) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode result = mapper.createObjectNode();
		
		String metricId = specification.path("metricid").textValue();
		String description = specification.path("metricid").textValue();
		result.put("metricid", metricId);
	}
	
	protected ArrayNode createDatatable(JsonNode datatableSpec, DBCollection collection) {
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
			Iterator<DBObject> it = collection.find().iterator();
			while(it.hasNext()) {
				DBObject dbobj = it.next();
				BasicDBList rows = (BasicDBList)dbobj.get(rowName);
				
				Iterator<Object> rowsIt = rows.iterator();
				while (rowsIt.hasNext()) {
					BasicDBObject row = (BasicDBObject) rowsIt.next();
					ObjectNode r = mapper.createObjectNode();

					for (int i = 0; i < colNames.size(); i++) {
						JsonNode col = colNames.get(i);
						String name  = col.path("name").textValue();
						String field = col.path("field").textValue();
						
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
			Iterator<DBObject> it = collection.find().iterator();
			while(it.hasNext()) {
				DBObject dbobj = it.next();
				ObjectNode r = mapper.createObjectNode();
				for (int i = 0; i < colNames.size(); i++) {
					JsonNode col = colNames.get(i);
					String name  = col.path("name").textValue();
					String field = col.path("field").textValue();
					
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

	public void completeFields(ObjectNode visualisation, JsonNode metricSpecification) {
		
	}
}
