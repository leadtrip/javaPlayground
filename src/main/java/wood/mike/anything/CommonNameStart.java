package wood.mike.anything;

import java.util.ArrayList;
import java.util.List;

public class CommonNameStart {
    public static void main(String[] args) {
        String[] names = {"rose", "roses", "rosemary", "ross"};

        String longestName = names[0];
        for (String name: names) {
            if(name.length() > longestName.length()){
                longestName = name;
            }
        }

        List<String> common = new ArrayList<>();

        for(int i = longestName.length(); i > 0; i--){
            String commonName = longestName.substring(0, i);
            for(String name : names){
                if(name.startsWith(commonName)) {
                    common.add(commonName);
                }
            }
            if(common.size() == names.length){
                break;
            }
            else {
                common.clear();
            }
        }
        System.out.println(common.getFirst());
    }
}
