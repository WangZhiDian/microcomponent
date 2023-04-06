package com.meng.config;

import com.meng.util.Base64Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringValueResolver;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
import java.util.*;

import static com.meng.define.CommonDefine.DEFAULT_ENCODING;


/**
 * encryptable property placeholder config
 *
 * @author : s00441405
 */
@Component
public class EncryptablePropertyPlaceholderConfig extends PlaceholderConfigurerSupport {
    private static final String KEY_STR = EncryptablePropertyPlaceholderConfig.class.getName();

    private static final String KEY_ALGORITHM_AES = "AES";
    private static final int KEY_BIT_LENGTH = 128;
    private static final String RANDOM_ALGORITHM_SHA1 = "SHA1PRNG";

    private static final String ENCRYPTED_PROPERTY_HEADER = "encrypt.";

    private static final String SCAN_PROPERTY_PATH_CLASSPATH = "classpath*:config/*.properties";
    private static final String SCAN_PROPERTY_PATH_FILE_FORMAT = "file:%s/config/*.properties";
    private static final String SCAN_PROPERTY_PATH_SPRING_CONFIG_LOCATION_FORMAT = "file:%s";

    /**
     * system property spring config location key
     */
    public static final String SYSTEM_PROPERTY_SPRING_CONFIG_LOCATION_KEY = "spring.config.location";

    /**
     * system property config dir key
     */
    public static final String SYSTEM_PROPERTY_CONFIG_DIR_KEY = "config.dir";
    private static final String SYSTEM_PROPERTY_SERVER_SSL_KEY_STORE_PASSWORD = "server.ssl.key-store-password";

    private Key aesKey;

    /**
     * encryptable property placeholder config
     *
     * @throws Exception :
     */
    public EncryptablePropertyPlaceholderConfig() throws Exception {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(KEY_ALGORITHM_AES);
            SecureRandom secureRandom = SecureRandom.getInstance(RANDOM_ALGORITHM_SHA1);
            secureRandom.setSeed(KEY_STR.getBytes());
            generator.init(KEY_BIT_LENGTH, secureRandom);
            aesKey = generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String configDir = System.getProperty(SYSTEM_PROPERTY_CONFIG_DIR_KEY);
        String springConfigLocation = System.getProperty(SYSTEM_PROPERTY_SPRING_CONFIG_LOCATION_KEY);

        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver
                = new PathMatchingResourcePatternResolver();
        Resource[] resourceClass =
                pathMatchingResourcePatternResolver.getResources(SCAN_PROPERTY_PATH_CLASSPATH);
        List<Resource> resourceList = new ArrayList<>(Arrays.asList(resourceClass));

        if (!StringUtils.isEmpty(configDir)) {
            Resource[] resourceFile =
                    pathMatchingResourcePatternResolver.getResources(
                            String.format(SCAN_PROPERTY_PATH_FILE_FORMAT, configDir));
            resourceList.addAll(Arrays.asList(resourceFile));
        }
        if (!StringUtils.isEmpty(springConfigLocation)) {
            Resource[] resourceSpringContext =
                    pathMatchingResourcePatternResolver.getResources(
                            String.format(SCAN_PROPERTY_PATH_SPRING_CONFIG_LOCATION_FORMAT, springConfigLocation));
            resourceList.addAll(Arrays.asList(resourceSpringContext));
        }

        this.setLocations(resourceList.toArray(new Resource[]{}));
        this.setFileEncoding(DEFAULT_ENCODING);
    }

    /**
     * get encrypt string
     *
     * @param str :
     * @return String :
     */
    public String getEncryptString(String str) {
        try {
            byte[] bytes = str.getBytes(DEFAULT_ENCODING);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_AES);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] doFinal = cipher.doFinal(bytes);
            return Base64Utils.encodeData(doFinal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get decrypt string
     *
     * @param str :
     * @return String :
     */
    public String getDecryptString(String str) {
        try {
            byte[] bytes = Base64Utils.decodeData(str.getBytes());
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_AES);
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] doFinal = cipher.doFinal(bytes);
            return new String(doFinal, DEFAULT_ENCODING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        try {
            Set<String> propertyNameSet = props.stringPropertyNames();
            for (String propertyName : propertyNameSet) {
                if (propertyName.startsWith(ENCRYPTED_PROPERTY_HEADER)) {
                    String newPropertyName = propertyName.substring(ENCRYPTED_PROPERTY_HEADER.length());

                    String encryptValue = props.getProperty(propertyName);
                    String decryptValue = getDecryptString(encryptValue);

                    props.setProperty(newPropertyName, decryptValue);

                    if (SYSTEM_PROPERTY_SERVER_SSL_KEY_STORE_PASSWORD.equals(newPropertyName)) {
                        System.setProperty(SYSTEM_PROPERTY_SERVER_SSL_KEY_STORE_PASSWORD, decryptValue);
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanInitializationException(e.getMessage());
        }

        StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(props);
        doProcessProperties(beanFactoryToProcess, valueResolver);
    }

    protected String resolvePlaceholder(String placeholder, Properties props) {
        String propVal = props.getProperty(placeholder);

        if (propVal == null) {
            propVal = resolveSystemProperty(placeholder);
        }
        return propVal;
    }

    protected String resolveSystemProperty(String key) {
        try {
            String value = System.getProperty(key);
            if (value == null) {
                value = System.getenv(key);
            }
            return value;
        } catch (Throwable ex) {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not access system property '" + key + "': " + ex);
            }
            return null;
        }
    }

    /**
     * placeholder resolving string value resolver
     */
    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final PropertyPlaceholderHelper helper;

        private final PropertyPlaceholderHelper.PlaceholderResolver resolver;

        /**
         * placeholder resolving string value resolver
         *
         * @param props :
         */
        PlaceholderResolvingStringValueResolver(Properties props) {
            this.helper = new PropertyPlaceholderHelper(
                    placeholderPrefix, placeholderSuffix, valueSeparator, ignoreUnresolvablePlaceholders);
            this.resolver = new PropertyPlaceholderConfigurerResolver(props);
        }

        /**
         * resolve string value
         *
         * @param strVal :
         * @return String :
         * @throws BeansException :
         */
        @Override
        @Nullable
        public String resolveStringValue(String strVal) throws BeansException {
            String resolved = this.helper.replacePlaceholders(strVal, this.resolver);
            if (trimValues) {
                resolved = resolved.trim();
            }
            return (resolved.equals(nullValue) ? null : resolved);
        }
    }

    /**
     * property placeholder configurer resolver
     */
    private final class PropertyPlaceholderConfigurerResolver implements PropertyPlaceholderHelper.PlaceholderResolver {

        private final Properties props;

        private PropertyPlaceholderConfigurerResolver(Properties props) {
            this.props = props;
        }

        /**
         * resolve placeholder
         *
         * @param placeholderName :
         * @return String :
         */
        @Override
        @Nullable
        public String resolvePlaceholder(String placeholderName) {
            return EncryptablePropertyPlaceholderConfig.this.resolvePlaceholder(placeholderName, this.props);
        }
    }


}
