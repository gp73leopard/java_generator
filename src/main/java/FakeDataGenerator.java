import com.github.javafaker.Faker;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


public class FakeDataGenerator {
    public static void main(String[] args) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        FileWriter fw = new FileWriter("data.csv");
        Faker faker = new Faker();
        System.out.println("Введите размер пула: ");
        int k = in.nextInt(); //ввод размера пула
        for (int i=0;i<k;i++){
            data.add(Name(faker)+";"+BDate(faker)+";"+Phone()+";"+Email(faker)+";"+Snils()); //запись в список
        }
        for (String str : data){
            fw.write(str+"\n"); //запись в файла из списка
            fw.flush();
        }
        fw.close();
    }
    public static String Name(Faker faker){
        faker = new Faker(new Locale("ru"));
        String fio = faker.name().fullName();
//        String alph = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
//        String fio = "";
//        Random r = new Random();
//        for (int i=0;i<3;i++)
//        {
//            for (int j=0;j<10;j++)
//            {
//                fio+=alph.charAt(r.nextInt(alph.length())); //генерация ФИО из алфавита русских букв
//            }
//            if(i!=2){
//                fio+=' '; //разделение ФИО через пробел
//            }
//        }
        return fio;
    }
    public static String Email(Faker faker){
        faker = new Faker(new Locale("en"));
        String email = faker.name().name().replaceAll("\\W","")+"@yandex.ru";
//        String email ="";
//        Random r = new Random();
//        String alph_en = "abcdefghijklmnopqrstuvwxyz";
//        for (int i=0;i<2;i++)
//        {
//            for (int j=0;j<6;j++)
//            {
//                email+=alph_en.charAt(r.nextInt(alph_en.length())); //генерация почты из английского алфавита
//            }
//            if(i==0)
//            {
//                email+='@';
//            }
//            if(i==1)
//            {
//                email+=".com";
//            }
//        }
        return email;
    }
    public static String BDate(Faker faker){
        Random r = new Random();
        int a=18,b=99; //границы возраста
        LocalDate currentDate = LocalDate.now();
        LocalDate date1 = currentDate.minus(Period.ofYears(a)); //граница даты когда человеку b-лет
        LocalDate date2 = currentDate.minus(Period.ofYears(b)); //граница даты когда человеку a-лет
        int days = (int)Math.abs(date2.toEpochDay()-date1.toEpochDay()); //расчет количества дней между датами
        LocalDate date3 = date2.plus(Period.ofDays(r.nextInt(days+1))); //генерация даты в периоде дат от a до b
        int day = date3.getDayOfMonth();
        int month = date3.getMonthValue();
        int year = date3.getYear();
        String birthday = "";
        if(day<10) //Форматирование для даты вида ДД.ММ.ГГГГ
        {
            birthday+="0";
        }
        birthday += day+".";
        if(month<10)
        {
            birthday+="0";
        }
        birthday+=month+"."+year;
        return birthday;
    }
    public static String Phone(){
        Random r = new Random();
        int count = r.nextInt(10000000);
        String phone = "8999";
        for (int i=String.valueOf(count).length();i<7;i++)
        {
            phone+='0';//добавление недостающих символов, если меньше 7 цифр
        }
        phone+=String.valueOf(count);
        return phone;
    }
    public static String Snils(){
        Random r = new Random();
        int snils_check = 1001998; //формирование снилса не меньше этого номера
        String snils = String.valueOf(r.nextInt(999999999-snils_check)+snils_check+1);
        if(snils.length()<9)//форматирование, если снилс меньше 9 цифр
        {
            String temp = snils;
            snils="";
            for(int i=temp.length();i<9;i++)
            {
                snils+="0";
            }
            snils+=temp;
        }
        int check_sum = 0;
        for (int i=0;i<snils.length();i++)
        {
            check_sum += Integer.parseInt(String.valueOf(snils.charAt(i)))*(snils.length()-i); // умножение 1 го числа на 9 позицию, 2 го на 8 и т.д.
        }
        check_sum%=101;
        if(check_sum==100)
        {
            check_sum=0;
        }
        if(check_sum<10)
        {
            snils+="0";
        }
        snils+=check_sum;
        StringBuilder sb = new StringBuilder(snils);
        sb.insert(3,"-"); //форматирование снилса
        sb.insert(7,"-");
        sb.insert(11," ");
        return sb.toString();
    }
}
