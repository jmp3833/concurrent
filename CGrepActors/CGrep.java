import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.Arrays;

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

        //Make our executor service, with a threadpool of 3 and a queue to store other callables in.
        ExecutorService executor = new ThreadPoolExecutor(3, 3, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(args.length - 1));

        //Array to store our futures in
        ArrayList<Future<List<String>>> futures = new ArrayList<Future<List<String>>>();

        //Submit each grep for execution and save the future
        for(String s : filenames) {
            GrepTask gt = new GrepTask(s, reg);
            Future<List<String>> f = executor.submit(gt);
            futures.add(f);
        }

        int files = filenames.length;
        int filesDone = 0;
        //Loop through futures and print results of done tasks
        while(filesDone < files) {
            for(int i = 0; i < futures.size(); i++) {
                if(futures.get(i).isDone()) {
                    filesDone++;
                    Future<List<String>> f = futures.get(i);
                    futures.remove(i);
                    try {
                        List<String> results = f.get();
                        for(String s : results) {
                            System.out.println(s);
                        }
                    } catch (InterruptedException ie) {
                        //Shouldn't happen
                        System.out.println("A task was interrupted");
                    } catch (ExecutionException ee) {
                        //Thrown if a file is not found
                        System.out.println(ee.getMessage());
                    }
                }
            }
        }

        //Exit
        executor.shutdown();
        //Wait for the executor to shutdown
        while(! executor.isShutdown());
        System.exit(0);
    }
}
