package no.sysco.middleware.kafka.interceptor.zipkin;

import java.util.AbstractList;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tracing Configuration wraps properties provided by a Kafka Client and enable access to
 * configuration values.
 */
public class TracingConfiguration {

	static final Logger LOGGER = LoggerFactory.getLogger(TracingConfiguration.class);

	public static final String LOCAL_SERVICE_NAME_CONFIG = "zipkin.local.service.name";

	public static final String LOCAL_SERVICE_NAME_DEFAULT = "kafka-client";

	public static final String TRACE_ID_128BIT_ENABLED_CONFIG = "zipkin.trace.id.128bit.enabled";

	public static final String TRACE_ID_128BIT_ENABLED_DEFAULT = "true";

	public static final String HTTP_ENDPOINT_CONFIG = "zipkin.http.endpoint";

	public static final String HTTP_ENDPOINT_DEFAULT = "http://localhost:9411/api/v2/spans";

	public static final String KAFKA_BOOTSTRAP_SERVERS_CONFIG = "zipkin.kafka.bootstrap.servers";

	public static final String ENCODING_CONFIG = "zipkin.encoding";

	public static final String ENCODING_DEFAULT = "JSON";

	public static final String SAMPLER_RATE_CONFIG = "zipkin.sampler.rate";

	public static final String SAMPLER_RATE_DEFAULT = "1.0F";

	public static final String SENDER_TYPE_CONFIG = "zipkin.sender.type";

	public static final String SENDER_TYPE_DEFAULT = "NONE";

	final Map<String, ?> configs;

	TracingConfiguration(Map<String, ?> configs) {
		this.configs = configs;
	}

	String getStringList(String configKey) {
		final String value;
		final Object valueObject = configs.get(configKey);
		if (valueObject instanceof AbstractList) {
			AbstractList valueList = (AbstractList) valueObject;
			value = String.join(",", valueList);
		}
		else {
			LOGGER.warn("{} of type ArrayList is not found in properties", configKey);
			value = null;
		}
		return value;
	}

	/**
	 * @return Value as String. If not found, then null is returned.
	 */
	String getStringOrDefault(String configKey, String defaultValue) {
		final String value;
		final Object valueObject = configs.get(configKey);
		if (valueObject instanceof String) {
			value = (String) valueObject;
		}
		else {
			LOGGER.warn("{} of type String is not found in properties", configKey);
			value = defaultValue;
		}
		return value;
	}

	String getString(String configKey) {
		return getStringOrDefault(configKey, null);
	}

}