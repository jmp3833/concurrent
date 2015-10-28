import java.util.regex.Pattern;

class CGrep {
    public static void main(String[] args) {
        if(args.length < 2) {
            System.err.println("Not enough arguments");
            System.exit(1);
        }
        String pattern = args[0];
        Pattern reg = Pattern.compile(pattern);

        String[] filenames = new String[args.length - 1];
        for(int i = 1; i < args.length; i++) {
            filenames[i - 1] = args [i]; 
        }
    }
}
