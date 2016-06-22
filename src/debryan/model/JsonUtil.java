package debryan.model;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class JsonUtil {

	private static ObjectMapper objectMapper;

	private static Map<Class<?>, ObjectWriter> owMap = new HashMap<Class<?>, ObjectWriter>();

	static {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
	}

	public static String jsonToString(Object obj) {
		String result = null;
		try {

			ObjectWriter ow = owMap.get(obj.getClass());

			if (ow == null) {
				ow = objectMapper.writerWithType(obj.getClass());
				owMap.put(obj.getClass(), ow);
			}

			result = ow.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static <T> T readJsonFromString(String source, Class<T> kClass) {
		T result = null;
		try {
			result = objectMapper.readValue(source, kClass);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
