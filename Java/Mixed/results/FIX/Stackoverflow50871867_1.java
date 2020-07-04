class Stackoverflow50871867_1{
private static JsonElement clean(JsonElement elem) {
    if(elem.isJsonPrimitive()) { // Base case - we have a Number, Boolean or String
        JsonPrimitive primitive = elem.getAsJsonPrimitive();
        if(primitive.isString()) {
            // Escape all String values
            return new JsonPrimitive(Encode.forHtml(primitive.getAsString()));
        } else {
            return primitive;
        }
    } else { // Recursive case - iterate over JSON object entries
        JsonObject obj = elem.getAsJsonObject();
        JsonObject clean = new JsonObject();
        for(Map.Entry<String, JsonElement> entry :  obj.entrySet()) {
            // Encode the key right away and encode the value recursively
            clean.add(Encode.forHtml(entry.getKey()), clean(entry.getValue()));
        }
        return clean;
    }
}
}