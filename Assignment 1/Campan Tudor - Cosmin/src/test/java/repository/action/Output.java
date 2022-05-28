package repository.action;

public class Output {
    final static short i=2;
    public static int j=0;

    enum Season {WINTER,SPRING,SUMMER,FALL};

    public static void main(String[] args) {
        for(int k=0; k<3;k++)
        {
            switch (k){
                case i:System.out.print("0");
                case i-1:System.out.print("1");
                case i-2:System.out.print("2\n");
            }
        }
        System.out.println(Season.SPRING.ordinal()+2+"code");

        String str = " Hellow";
        System.out.println(str.indexOf('t'));
    }
}
