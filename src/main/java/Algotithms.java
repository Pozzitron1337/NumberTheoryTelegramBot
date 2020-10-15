import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Algotithms {

    Algotithms() {}

    public BigInteger GornerScheme(BigInteger a, BigInteger n, BigInteger m) {//a^n(mod(m))
        BigInteger b = new BigInteger("1");
        for (int i = 0; i < n.bitLength(); i++) {
            b = (b.multiply(b)).mod(m);
            if (n.toString(2).charAt(i) == '1')
                b = (b.multiply(a)).mod(m);
        }
        return b;
    }//a^n mod(m)

    public static BigInteger pow(BigInteger a, BigInteger n) {//a^n
        BigInteger b = new BigInteger("1");
        for (int i = 0; i < n.bitLength(); i++) {
            b = b.multiply(b);
            if (n.toString(2).charAt(i) == '1')
                b = b.multiply(a);
        }
        return b;
    } //a^n(GornerSheme)

    public BigInteger Power(BigInteger a,BigInteger n,BigInteger m){
        if(n.compareTo(m)==1 && (a.gcd(m)).compareTo(BigInteger.ONE)==0){
            BigInteger ksi = GeneralizedEulerFunction(n);
            BigInteger n0=n.mod(ksi);
            return GornerScheme(a,n0,m);

        }else
        return GornerScheme(a,n,m);
    }//a^n mod(m) if n>>m

    public BigInteger EulerFunction(BigInteger n) {
        if (n.compareTo(BigInteger.ONE) == 0) return BigInteger.ONE;
        Map<BigInteger, BigInteger> primes = primeFactors(n);
        BigInteger result = BigInteger.ONE;
        for (BigInteger p : primes.keySet()) {
            result = result.multiply(pow(p, primes.get(p)).subtract(pow(p, primes.get(p).subtract(BigInteger.ONE))));
        }
        return result;
    } //phi(n)

    public Map<BigInteger, BigInteger> primeFactors(BigInteger n) {
        BigInteger primeFactor = BigInteger.ZERO;
        BigInteger num = n;
        BigInteger j = BigInteger.ZERO;
        Map<BigInteger, BigInteger> primeFactors = new HashMap<BigInteger, BigInteger>();
        for (BigInteger i = new BigInteger("2"); i.compareTo(num.divide(i)) <= 0; ) {
            if (num.mod(i).longValue() == 0) {
                primeFactor = i;
                primeFactors.put(primeFactor, j = j.add(BigInteger.ONE));
                num = num.divide(i);
            } else {
                i = i.add(BigInteger.ONE);
                j = BigInteger.ZERO;
            }
        }
        if (primeFactor.compareTo(num) < 0)
            primeFactors.put(num, BigInteger.ONE);
        else
            primeFactors.put(primeFactor, j.add(BigInteger.ONE));
        return primeFactors;
    } //factorization (prime ->mutiplicity)

    public ArrayList<BigInteger> primeBigFactorize(BigInteger n) {
        ArrayList<BigInteger> primeFactors = new ArrayList<BigInteger>();
        BigInteger primeFactor = BigInteger.ZERO;

        for (BigInteger i = new BigInteger("2"); i.compareTo(n.divide(i)) <= 0; ) {
            if (n.mod(i).longValue() == 0) {
                primeFactor = i;
                primeFactors.add(primeFactor);
                n = n.divide(i);
            } else {
                i = i.add(BigInteger.ONE);
            }
        }
        if (primeFactor.compareTo(n) < 0) {
            primeFactors.add(n);
        } else {
            primeFactors.add(primeFactor);
        }
        return primeFactors;
    }//factorization (array of primes)

    public BigInteger lcm(BigInteger a,BigInteger b) {
        return (a.multiply(b)).divide(a.gcd(b));

    }//lcm

    public BigInteger GeneralizedEulerFunction(BigInteger n){
        Map<BigInteger,BigInteger> primeNumbers=new HashMap<BigInteger, BigInteger>();
        primeNumbers=primeFactors(n);
        if(primeNumbers.size()==1 && primeNumbers.values().size()==1)
            return n.subtract(BigInteger.ONE);
        ArrayList<BigInteger> ni=new ArrayList<BigInteger>();

        for(BigInteger i:primeNumbers.keySet()) {
            ni.add((pow(i, primeNumbers.get(i))).subtract(pow(i, primeNumbers.get(i).subtract(BigInteger.ONE))));
        }
            BigInteger result=BigInteger.ONE;
        for(int i=0;i<ni.size()-1;i++) {
            result = lcm(lcm(result,ni.get(i)), ni.get(i + 1));
        }
        return result;

    }//ksi(n)

    public BigInteger SolvingCongruence(BigInteger a,BigInteger b,BigInteger n){
        BigInteger d=(a.gcd(b)).gcd(n);
        a=a.divide(d);
        b=b.divide(d);
        n=n.divide(d); //(*)
        if((a.gcd(n)).gcd(b).compareTo(BigInteger.ONE)!=0){
            try {
                throw new NullPointerException();
            } catch (NullPointerException e) {
                throw e;
            }
        }
        d=a.gcd(b);
        a=a.divide(d);
        b=b.divide(d);
        return b.multiply(a.modInverse(n)).mod(n);// (b*a^-1)%n+k*n,kєZ and n can be redefined in algorithm (*)



    }//ax=b (mod(n))

    public BigInteger ModInverse(BigInteger a,BigInteger n){
        if(a.gcd(n).compareTo(BigInteger.ONE)!=0){
            try {
                throw new NullPointerException();
            } catch (NullPointerException e) {
                throw e;
            }
        }
        LineCombinationNumber l=new LineCombinationNumber(a,n);
        BigInteger u=l.getU();
        return u.mod(n);
    }//a^-1 mod(n)

    public BigInteger SolvingSystemOfTwoCongruence(ArrayList<BigInteger> x,ArrayList<BigInteger> n) {
     /*
     |x=x1 mod(n1)
     |x=x2 mod(n2)
     find x;
     */
        if((n.get(0).gcd(n.get(1))).compareTo(BigInteger.ONE)!=0) {
            try {
                throw new NullPointerException();
            } catch (NullPointerException e) {
                throw e;
            }
        }
        LineCombinationNumber l=new LineCombinationNumber(n.get(0),n.get(1));
        BigInteger sum=((l.getU()).multiply(n.get(0)).multiply(x.get(1))).add(l.getV().multiply(n.get(1)).multiply(x.get(0)));
        return sum.mod(n.get(0).multiply(n.get(1)));// sum%(n1*n2)+k*n1*n2,kєZ
    }

    public BigInteger SolvingSystemOfCongruence(ArrayList<BigInteger> x,ArrayList<BigInteger> m) {
        /*
        |x=x1 mod(m1)
        |x=x2 mod(m2)
        |...
        |x=xi mod(mi)
        iє{3,...,x.size()},x.size()==m.size();
         */
        if(x.size()!=m.size()){
            try {
                throw new NullPointerException();
            } catch (NullPointerException e) {
                throw e;
            }
        }
        for(int i=2;i<m.size();i++){
            if((m.get(i-2).gcd(m.get(i-1))).gcd(m.get(i)).compareTo(BigInteger.ONE)!=0){
                try {
                    throw new NullPointerException();
                } catch (NullPointerException e) {
                    throw e;
                }
            }
        }
        BigInteger M=new BigInteger("1");
        for(int i=0;i<m.size();i++)
            M=M.multiply(m.get(i));

        ArrayList<BigInteger> Mi=new ArrayList<BigInteger>();
        for(int i=0;i<m.size();i++)
            Mi.add(M.divide(m.get(i)));

        ArrayList<BigInteger> Ni=new ArrayList<BigInteger>();
        for(int i=0;i<m.size();i++)
            Ni.add(ModInverse(Mi.get(i),m.get(i)));

        BigInteger sum=new BigInteger("0");
        for(int i=0;i<m.size();i++)
            sum=sum.add(Ni.get(i).multiply(Mi.get(i)).multiply(x.get(i)));

        return sum.mod(M);//sum+k*M,kєZ

    }//returns 1 solution

    public ArrayList<BigInteger> FindingGeneratorsInCyclycGroup(BigInteger p) {
        ArrayList<BigInteger> res=new ArrayList<BigInteger>();

        if(p.compareTo(BigInteger.TWO)==0) {
            res.add(BigInteger.ONE);
            return res;
        }
        Map<BigInteger,BigInteger> primes=primeFactors(p.subtract(BigInteger.ONE));
        BigInteger g=BigInteger.TWO;
        BigInteger gi;
        boolean flag=false;
        while(g.compareTo(p)==-1){

            for(BigInteger r:primes.keySet()){
                gi=Power(g,(p.subtract(BigInteger.ONE)).divide(r),p);
                if(gi.compareTo(BigInteger.ONE)==0){
                    flag=true;
                    break;
                }
            }
            if(flag){
                flag=false;
                g=g.add(BigInteger.ONE);
            }else
                break;
        }
        ArrayList<BigInteger> S=new ArrayList<BigInteger>();
        for(BigInteger i=BigInteger.ONE;i.compareTo(p)==-1;i=i.add(BigInteger.ONE)){
            if(i.gcd(p.subtract(BigInteger.ONE)).compareTo(BigInteger.ONE)==0)
                S.add(i);
        }


        for(BigInteger s:S)
            res.add(Power(g,s,p));
        return res;
    }//Generators in Z*

    public  BigInteger SymbolJacobi(BigInteger x,BigInteger n){
        Map<BigInteger,BigInteger> pr=primeFactors(n);
        BigInteger res=new BigInteger("1");
        BigInteger current;
        for(BigInteger i:pr.keySet()) {
            if (x.mod(i).equals(BigInteger.ZERO)){
                res = BigInteger.ZERO;
                break;
            }
            current=Power(x,(i.subtract(BigInteger.ONE)).divide(BigInteger.TWO),i);
            if(current.equals(i.subtract(BigInteger.ONE))){
                res=pow(res.multiply(BigInteger.valueOf(-1)),pr.get(i));
            }else{
                res=pow(res,pr.get(i));
            }

        }
        return res;
    }

    public boolean isPrime(BigInteger a){
        return a.isProbablePrime(999);
    }

    public ArrayList<BigInteger> SquareRoots(BigInteger a,BigInteger p) {
        ArrayList<BigInteger> res = new ArrayList<BigInteger>();

        if((p.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)))){
            if (!Power(a,(p.subtract(BigInteger.ONE)).divide(BigInteger.TWO),p).equals(BigInteger.ONE))return res;
                BigInteger k=(p.subtract(BigInteger.valueOf(3))).divide(BigInteger.valueOf(4));
                BigInteger y1=Power(a,k.add(BigInteger.ONE),p);
                res.add(y1);
                res.add(p.subtract(y1));
                return res;
        }
        if(p.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(5))){
            if(!Power(a,(p.subtract(BigInteger.ONE)).divide(BigInteger.TWO),p).equals(BigInteger.ONE))return res;
            BigInteger k=(p.subtract(BigInteger.valueOf(5))).divide(BigInteger.valueOf(8));
            BigInteger X=Power(a,(BigInteger.valueOf(2).multiply(k)).add(BigInteger.ONE),p);
            BigInteger y1;
            if(X.equals(BigInteger.ONE)){
                y1=Power(a,k.add(BigInteger.ONE),p);
                res.add(y1);
                res.add(p.subtract(y1));
            }else{
                y1=(pow(a,k.add(BigInteger.ONE)).multiply(pow(BigInteger.TWO,(BigInteger.TWO.multiply(k)).add(BigInteger.ONE)))).mod(p);
                res.add(y1);
                res.add(p.subtract(y1));
            }
            return res;

        }

        if(p.mod(BigInteger.valueOf(8)).equals(BigInteger.ONE)){
        if (!SymbolJacobi(a, p).equals(BigInteger.ONE)) {
            return res;
        }
        BigInteger x1 = new BigInteger("2");

        while (SymbolJacobi(x1, p).equals(BigInteger.ONE)) {
            x1 = x1.add(BigInteger.ONE);
        }
        BigInteger s0=(p.subtract(BigInteger.ONE)).divide(BigInteger.TWO);
        BigInteger s = (p.subtract(BigInteger.ONE)).divide(BigInteger.TWO);
        BigInteger s1 = (p.subtract(BigInteger.ONE)).divide(BigInteger.TWO);
        BigInteger tempx = Power(a, s, p);

        while (!tempx.equals(p.subtract(BigInteger.ONE))) {
            s = s.divide(BigInteger.TWO);
            tempx = Power(a, s, p);
        }
        BigInteger x = (pow(x1, s0).multiply(pow(a, s))).mod(p);

        while (!x.equals(p.subtract(BigInteger.ONE))){
            if(!s.mod(BigInteger.TWO).equals(BigInteger.ZERO)){
                s=(s.add(BigInteger.ONE)).divide(BigInteger.TWO);
                s1=s1.divide(BigInteger.TWO);
                x = (pow(x1, s1).multiply(pow(a, s))).mod(p);
                res.add(x);
                res.add(p.subtract(x));
                return res;
            }
        s = s.divide(BigInteger.TWO);
        s1 = s1.divide(BigInteger.TWO);
        x = (pow(x1, s1).multiply(pow(a, s))).mod(p);
        }

        x=(pow(x1, (s1.add(s0)).divide(BigInteger.TWO)).multiply(pow(a, (s.add(BigInteger.ONE)).divide(BigInteger.TWO))).mod(p));
        res.add(x);
        res.add(p.subtract(x));

        return res;
        }
        return res;
    }

    public ArrayList<BigInteger> SquareRoots_version2(BigInteger a,BigInteger p){
        ArrayList<BigInteger> result=new ArrayList<>();
        BigInteger i=BigInteger.ONE;

        while(i.compareTo(p.subtract(BigInteger.ONE))<1){
            if(i.modPow(BigInteger.TWO,p).compareTo(a)==0){
                result.add(i);
            }
            i=i.add(BigInteger.ONE);
        }
        return result;
    }

    public ArrayList<BigInteger> PseudoSimplesByFerma(BigInteger n){
        ArrayList<BigInteger> result=new ArrayList<BigInteger>();
        BigInteger i =new BigInteger("1");
        while (!i.equals(n)){
            if(Power(i,n.subtract(BigInteger.ONE),n).equals(BigInteger.ONE)) result.add(i);
            i=i.add(BigInteger.ONE);
        }
        return result;
    }


}