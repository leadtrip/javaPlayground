package wood.mike.itso;

public class UserDefinedField implements ItsoElement{

    private String value;

    public UserDefinedField(final String value) {
        this.value = value;
    }

    @Override
    public String toTransportFormat() {
        char[] chars = value.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char ch : chars) {
            hex.append(Integer.toHexString(ch));
        }
        return hex.toString();
    }

    public String fromTransportFormat(final String hexStr) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }
}
