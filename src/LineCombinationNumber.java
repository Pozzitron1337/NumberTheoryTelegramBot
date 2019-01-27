import java.math.BigInteger;

class LineCombinationNumber {
    //a,b,d є N
    //u,v є Z
    //d=(a,b)
    //d=u*a+v*b
    //a^-1 mod(m)=u => a*a^-1=1
    BigInteger a;
    BigInteger b;
    BigInteger d;
    BigInteger u;
    BigInteger v;

    LineCombinationNumber(BigInteger a,BigInteger b)
    {
        this.a=a;
        this.b=b;

        ExtendedEuclicAlgorithm(a,b);
    }

    public void ExtendedEuclicAlgorithm(BigInteger a1, BigInteger b1) {
        BigInteger q,r,x1,x2,y1,y2;
        if (b1.compareTo(BigInteger.ZERO)==0){
            this.d=a1;
            this.u=BigInteger.ONE;
            this.v=BigInteger.ZERO;
        }
        x2=BigInteger.ONE;
        x1=BigInteger.ZERO;
        y2=BigInteger.ZERO;
        y1=BigInteger.ONE;

        while(b1.compareTo(BigInteger.ZERO)==1){//b1>0
            q=a1.divide(b1);
            r=a1.subtract(q.multiply(b1));
            u=x2.subtract(q.multiply(x1));
            v=y2.subtract(q.multiply(y1));
            a1=b1;
            b1=r;
            x2=x1;
            x1=u;
            y2=y1;
            y1=v;
        }
        d=a1;
        u=x2;
        v=y2;
    }

    public BigInteger getU(){
        return u;
    }
    public BigInteger getV(){
        return v;
    }
}