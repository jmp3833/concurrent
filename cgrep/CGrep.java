import java.util.regex.Pattern;

class CGrep {
    public static void main(String[] args) {
        String pattern = args[0];

        String[] filenames = new String[args.length - 1];
        for(int i = 1; i < args.length; i++) {
            filenames[i - 1] = args [i]; 
        }
    }
}
