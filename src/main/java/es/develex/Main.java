package es.develex;

public class Main {

    public static void main(String[] args) {

        try {
            InputParameters inputParamters = new InputParameters(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
