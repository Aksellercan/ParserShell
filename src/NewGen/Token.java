package NewGen;

public class Token {
    private String key;
    private Object value;
    private boolean object = false;
    private boolean array = false;

    public Token(String key, Object value, boolean object, boolean array) {
        this.key = key;
        this.value = value;
        this.object = object;
        this.array = array;
    }

    public Token(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isObject() {
        return object;
    }

    public void setObject(boolean object) {
        this.object = object;
    }

    public boolean isArray() {
        return array;
    }

    public void setArray(boolean array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return "Token{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", object=" + object +
                ", array=" + array +
                '}';
    }
}
