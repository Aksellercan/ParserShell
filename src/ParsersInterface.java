public interface ParsersInterface extends ConfigurationInterface {
    void ReadConfig();
    void WriteConfig();

    /**
     * Find and set Token value, additionally mark it as seen to avoid duplicates
     * @param key   Key to modify
     * @param value Token value
     */
    default void FindAndSetToken(String key, String value) {
        for (Token token : tokenConfig) {
            if (!token.isSeen() && token.getKey().equals(key)) {
                token.setValue(value);
                token.markAsSeen();
                Logger.DEBUG.Log(":: CHECK " + token);
                break;
            }
        }
    }
}
