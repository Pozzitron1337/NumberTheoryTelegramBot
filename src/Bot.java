import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Bot extends TelegramLongPollingBot {
    public String menu= "0. modulus a mod b\n"+
                        "1. GornerSheme a^n modm\n" +
                        "2. Power a^n modm\n"+
                        "3. pow a^n\n"+
                        "4. EulerFunction n\n"+
                        "5. GeneralizedEulerFunction n\n"+
                        "6. Factorization n\n"+
                        "7. MapFactorization n\n"+
                        "8. SolvingCongruence ax=b modn\n"+
                        "9. SolvingSystemOfTwoCongruence [x1,x2][m1,m2]\n"+
                        "10. SolvingSystemOfCongruence [x1,...,xn][m1,...mn]\n"+
                        "11. lcm x1 x2\n"+
                        "12. GeneratorsInCyclicGroup p\n"+
                        "13. SymbolJacobi x n\n"+
                        "14. Root x p\n"+
                        "15. PseudoSimplesFerma n\n"+
                        "16. gcd a b\n"+
                        "Example request: 'GornerScheme 4^78mod54' or '1 3^33mod8';\n" +
                                            "'pow 2^99' or '3 7^29'";
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi=new TelegramBotsApi();
        try{
            telegramBotsApi.registerBot(new Bot());

        }catch (TelegramApiException e){
            e.printStackTrace();
        }

    }



    @Override
    public void onUpdateReceived(Update update) {
        Algotithms algorithm=new Algotithms();
        Message message= update.getMessage();
        String[] msg=message.getText().split("\\s",2);
        if(message!=null && message.hasText()){

            if(message.getText().equals("help")){
                sendMsg(message,menu);
            }
            if(msg[0].equals("modulus")||msg[0].equals("0")){
                Pattern mod=Pattern.compile("(\\d*)\\s*mod\\s*(\\d*)");
                Matcher modmatch=mod.matcher(msg[1]);
                modmatch.find();
                BigInteger a=new BigInteger(modmatch.group(1));
                BigInteger b=new BigInteger(modmatch.group(2));
                sendMsg(message,a.mod(b).toString());
            }
            if(msg[0].equals("GornerScheme")||msg[0].equals("1")){
                Pattern gornerscheme=Pattern.compile("^(\\d*)\\^(\\d*)\\s*mod(\\d*)");
                Matcher gornerschemematch=gornerscheme.matcher(msg[1]);
                gornerschemematch.find();
                BigInteger a=new BigInteger(gornerschemematch.group(1));
                BigInteger b=new BigInteger(gornerschemematch.group(2));
                BigInteger c=new BigInteger(gornerschemematch.group(3));
                String answer=algorithm.GornerScheme(a,b,c).toString();
                sendMsg(message,answer);
            }
            if(msg[0].equals("Power")||msg[0].equals("2")){
                Pattern power=Pattern.compile("^(\\d*)\\^(\\d*)\\s*mod(\\d*)");
                Matcher powermatch=power.matcher(msg[1]);
                powermatch.find();
                BigInteger a=new BigInteger(powermatch.group(1));
                BigInteger b=new BigInteger(powermatch.group(2));
                BigInteger c=new BigInteger(powermatch.group(3));
                String answer=algorithm.Power(a,b,c).toString();
                sendMsg(message,answer);
            }
            if(msg[0].equals("pow")||msg[0].equals("3")){
                Pattern pow=Pattern.compile("^(\\d*)\\^(\\d*)");
                Matcher powmatch=pow.matcher(msg[1]);
                powmatch.find();
                BigInteger a=new BigInteger(powmatch.group(1));
                BigInteger b=new BigInteger(powmatch.group(2));
                String answer=algorithm.pow(a,b).toString();
                sendMsg(message,answer);
            }
            if(msg[0].equals("EulerFunction")||msg[0].equals("4")){
                Pattern eulerfunction=Pattern.compile("(\\d*)");
                Matcher eulerfunctionmatch=eulerfunction.matcher(msg[1]);
                eulerfunctionmatch.find();
                BigInteger n=new BigInteger(eulerfunctionmatch.group(1));
                String answer=algorithm.EulerFunction(n).toString();
                sendMsg(message,answer);
            }
            if(msg[0].equals("GeneralizedEulerFunction")||msg[0].equals("5")){
                Pattern geneulerfunction=Pattern.compile("(\\d*)");
                Matcher geneulerfunctionmatch=geneulerfunction.matcher(msg[1]);
                geneulerfunctionmatch.find();
                BigInteger n=new BigInteger(geneulerfunctionmatch.group(1));
                String answer=algorithm.GeneralizedEulerFunction(n).toString();
                sendMsg(message,answer);
            }
            if(msg[0].equals("Factorization")||msg[0].equals("6")){
                Pattern factorization=Pattern.compile("(\\d*)");
                Matcher factorizationmatch=factorization.matcher(msg[1]);
                factorizationmatch.find();
                BigInteger n=new BigInteger(factorizationmatch.group(1));
                String answer="";
                for(BigInteger i:algorithm.primeBigFactorize(n)){
                    answer=answer+i.toString()+",";
                }
                sendMsg(message,answer);
            }
            if(msg[0].equals("MapFactorization")||msg[0].equals("7")){
                Pattern mapfactorization=Pattern.compile("(\\d*)");
                Matcher mapfactorizationmatch=mapfactorization.matcher(msg[1]);
                mapfactorizationmatch.find();
                BigInteger n=new BigInteger(mapfactorizationmatch.group(1));
                String answer="";
                Map<BigInteger,BigInteger> m=algorithm.primeFactors(n);
                for(BigInteger i:m.keySet()){
                    answer=answer+i+"->"+m.get(i)+"  ";
                }
                sendMsg(message,answer);
            }
            if(msg[0].equals("SolvingCongruence")||msg[0].equals("8")){
                Pattern solvingconguerence=Pattern.compile("(\\d*)\\s*x\\s*=\\s*(\\d*)\\s*mod\\s*(\\d*)");
                Matcher solvingconguerencematch=solvingconguerence.matcher(msg[1]);
                solvingconguerencematch.find();
                BigInteger a=new BigInteger(solvingconguerencematch.group(1));
                BigInteger b=new BigInteger(solvingconguerencematch.group(2));
                BigInteger n=new BigInteger(solvingconguerencematch.group(3));
                sendMsg(message,algorithm.SolvingCongruence(a,b,n).toString()+"+k"+n+",kєZ");
            }
            if(msg[0].equals("SolvingSystemOfTwoCongruence")||msg[0].equals("9")){
                Pattern solvingsystemoftwoconguerence=Pattern.compile("(\\d*)[, ]*(\\d*)[, ]*(\\d*)[, ]*(\\d*)");
                Matcher solvingsystemoftwoconguerencematch=solvingsystemoftwoconguerence.matcher(msg[1]);
                solvingsystemoftwoconguerencematch.find();
                BigInteger x1=new BigInteger(solvingsystemoftwoconguerencematch.group(1));
                BigInteger x2=new BigInteger(solvingsystemoftwoconguerencematch.group(2));
                BigInteger m1=new BigInteger(solvingsystemoftwoconguerencematch.group(3));
                BigInteger m2=new BigInteger(solvingsystemoftwoconguerencematch.group(4));
                BigInteger M=m1.multiply(m2);
                ArrayList<BigInteger> x=new ArrayList<BigInteger>();
                x.add(x1);
                x.add(x2);
                ArrayList<BigInteger> m=new ArrayList<BigInteger>();
                m.add(m1);
                m.add(m2);
                sendMsg(message,algorithm.SolvingSystemOfTwoCongruence(x,m).toString()+"+k"+M.toString()+",kєZ");
            }
            if(msg[0].equals("lcm")||msg[0].equals("11")){
                Pattern lcm=Pattern.compile("(\\d*)\\s*(\\d*)");
                Matcher lcmmatch=lcm.matcher(msg[1]);
                lcmmatch.find();
                BigInteger a=new BigInteger(lcmmatch.group(1));
                BigInteger b=new BigInteger(lcmmatch.group(2));
                sendMsg(message,algorithm.lcm(a,b).toString());
            }
            if(msg[0].equals("GeneratorsInCyclicGroup")||msg[0].equals("12")){
                Pattern gen=Pattern.compile("(\\d*)");
                Matcher genmatch=gen.matcher(msg[1]);
                genmatch.find();
                BigInteger n=new BigInteger(genmatch.group(1));
                String answer="";
                for(BigInteger i:algorithm.FindingGeneratorsInCyclycGroup(n)){
                    answer=answer+i+" ";
                }
                sendMsg(message,answer);
            }
            if(msg[0].equals("SymbolJacobi")||msg[0].equals("13")){
                Pattern symboljacobi=Pattern.compile("(\\d*)\\s*[\\/,\\s]\\s*(\\d*)");
                Matcher symboljacobimatch=symboljacobi.matcher(msg[1]);
                symboljacobimatch.find();
                BigInteger x=new BigInteger(symboljacobimatch.group(1));
                BigInteger n=new BigInteger(symboljacobimatch.group(2));
                sendMsg(message,algorithm.SymbolJacobi(x,n).toString());
            }
            if(msg[0].equals("Root")||msg[0].equals("14")){
                Pattern root=Pattern.compile("(\\d*)\\s*[\\/,\\s]\\s*(\\d*)");
                Matcher rootmatch=root.matcher(msg[1]);
                rootmatch.find();
                BigInteger x=new BigInteger(rootmatch.group(1));
                BigInteger n=new BigInteger(rootmatch.group(2));
                sendMsg(message,algorithm.SquareRoots(x,n).toString());
            }
            if(msg[0].equals("PseudoSimplesFerma")||msg[0].equals("15")){
                Pattern ferma=Pattern.compile("(\\d*)");
                Matcher fermamatch=ferma.matcher(msg[1]);
                fermamatch.find();
                BigInteger n=new BigInteger(fermamatch.group(1));
                sendMsg(message,algorithm.PseudoSimplesByFerma(n).toString());
            }
            if(msg[0].equals("gcd")||msg[0].equals("16")){
                Pattern gcd=Pattern.compile("(\\d*)\\s*[\\/,\\s]\\s*(\\d*)");
                Matcher gcdmatch=gcd.matcher(msg[1]);
                gcdmatch.find();
                BigInteger a=new BigInteger(gcdmatch.group(1));
                BigInteger b=new BigInteger(gcdmatch.group(2));
                sendMsg(message,a.gcd(b).toString());
            }
        }
    }


    public void sendMsg(Message message,String s){
        SendMessage sendMessage=new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(s);
        try{
            execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public static BotInformation botinformation=new BotInformation();//close information

    @Override
    public String getBotUsername() {
        return botinformation.getName();
    }

    @Override
    public String getBotToken() {
        return botinformation.getToken();
    }
}
