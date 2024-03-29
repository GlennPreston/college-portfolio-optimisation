package project;

public class Project {
    public static void main(String[] args){
        //Take in file
        FileIO io = new FileIO();
        String[] original = io.load("C:\\Users\\Glenn\\Documents\\StockData.txt");
        int numrows=original.length;
        int numcols=original[0].split("\t").length;
        double[][] array = new double[numrows][numcols];
        for(int i=1;i<numrows;i++){
            for(int j=1;j<numcols;j++){
                array[i][j]=Double.parseDouble(original[i].split("\t")[j]);
            }
        }
        
        //Variables
        double drawdown=0;
        String startdate="";
        String finishdate="";
        String company="";
        double[] stockPrice = {0,5108,1585,33384,4071,2394,6308,3153,3731,2923,5056,3870,2613,3448,1280,4367,4451,8414,2992,2446,3494,3149,1559,2916,4637,1256,711,5579,5523,5270,20949,3694,6615,5035,12159,7703,9021,5414,4646,6767,6004,12393,2600,3625,5075,29762,7043,1098,5673,5939,2559,2982,10973,8482,4292,7092,3396,10850,2480,18333,3827,5579,3182,2680,3345,723,5771,10242,4277,2155,2398,4395,5043,10333,5998,2539,2774,2839,3580,6088,3127,13684,2909,7672,5212,2672,4189,8728	,6333	,3401	,2463	,5504	,10331	,1838	,1811	,4686	,5179	,1674	,6317	,5985	,5256	,7420	,4623	,3218	,962	,3735	,3654	,1499	,2537	,3284	,3497	,7480	,8078	,2379	,3673	,9676	,4474	,5042	,8026	,1643	,1104	,2578	,5787	,1140	,5429	,3847	,4167	,7291	,2005	,6269	,6616	,3338	,4842	,4625	,5176	,5145	,5931	,4360	,7643	,3274	,5520	,4925	,3387	,3635	,5162	,2761	,4963	,5332	,10161	,2040	,5770	,5211	,5396	,1445	,4921	,6112	,8807	,3853	,5096	,4249	,1348	,3509	,4756	,5230	,9519	,3986	,11316	,950	,2045	,2969	,6374	,1223	,3326	,6256	,3603	,4300	,4017	,13306	,4449	,654	,3748	,1321	,7137	,1778	,2098	,3480	,1767	,2531	,1056	,52103	,5174	,1761	,13220	,1729	,15288	,5005	,4549	,4177	,625	,756	,4707	,3350	,3485	,7413	,2587	,5039	,4079	,5690	,5624	,6611	,3502	,1483	,2881	,4254	,5567	,1685	,5459	,8070	,16788	,12698	,6178	,1729	,2075	,5110	,2844	,1205	,4552	,2844	,37865	,11534	,5433	,2218	,2004	,4023	,3418	,1701	,4335	,6229	,3197	,900	,3893	,4606	,5156	,806	,3413	,3865	,6157	,3368	,3196	,2399	,4884	,4153	,2248	,1851	,9842	,5233	,8306	,3122	,3408	,3262	,7394	,2828	,2274	,722	,3463	,3307	,1151	,2744	,2861	,31255	,3361	,1183	,2585	,7988	,3502	,8242	,3764	,3679	,4179	,4727	,2966	,9125	,2352	,2477	,6982	,3245	,3135	,2320	,2441	,4452	,8292	,783	,5239	,3151	,1487	,2497	,4423	,2468	,2552	,3824	,5354	,5026	,26799	,6925	,1893	,4444	,6521	,7683	,2428	,7244	,5411	,4389	,3304	,3837	,1579	,1531	,3165	,2618	,3529	,4608	,3235	,6621	,9957	,2836	,1213	,1928	,4909	,3876	,3769	,52480	,16558	,3255	,6554	,1907	,2858	,5979	,2076	,8771	,777	,2683	,3357	,5589	,5753	,4106	,1755	,8756	,2507	,5981	,10783	,2054	,10405	,8993	,5569	,5548	,3317	,3916	,618	,2629	,4640	,13143	,8440	,8269	,3972	,5610	,1667	,2900	,1255	,4626	,1496	,3892	,3615	,1610	,2547	,2241	,7213	,8314	,7241	,7283	,8467	,1585	,6072	,4263	,3692	,11121	,1480	,9122	,4938	,2584	,4617	,4396	,2181	,905	,6953	,4380	,2191	,5812	,1958	,2898	,2813	,4240	,6091	,1715	,4691	,1518	,4551	,2604	,1787	,7611	,2591	,319	,4192	,6405	,5808	,5555	,1905	,2262	,1808	,3489	,3172	,2376	,2355	,5138	,2478	,10179	,6914	,2892	,2473	,8529	,7087	,10641	,4957	,2245	,3934	,8678	,3388	,4899	,3396	,4031	,9763	,3569	,2953	,2707	,858	,7802	,1040	,7747	,3424	,2302	,5058	,6985	,39540	,1908	,2090	,3371	,12818	,4604	,2258	,2133	,3507	,7741	,3801	,1016	,1545	,5434	,2426	,6387	,1340};
        String[] companies = new String[numcols];
        double[] volatility = new double[numcols];
        double[] change = new double[880];
        
        for(int j=1;j<numcols;j++){ //repeat for all companies
            double current=100; //start current price at 100%
            double peak=100;    //start peak is 100%
            double trough=0;
            String localstartdate="";
            String localfinishdate="";
            String recorddate="";

            for(int i=numrows-1;i>0;i--){   
                //go through each day - data is backwards
                current=current+(current*(array[i][j]/100));    
                //change the price for today
                if(current>peak){   //if it's a record high update
                    peak=current;
                    recorddate=original[i].split("\t")[0];  
                    //keep track of the date
                }else if(1-current/peak>trough){    
                    //otherwise, are we lower than ever before below the current peak?
                    trough=1-current/peak;  
                    //keep track of this super low
                    localstartdate=recorddate;
                    localfinishdate=original[i].split("\t")[0];
                }
            }
            
            drawdown=trough;
            startdate=localstartdate;
            finishdate=localfinishdate;
            company=original[0].split("\t")[j];
            if(drawdown*100<30.0) {
                System.out.println(company+" suffered a drawdown of "+String.format("%.1f",drawdown*100)+"% between the dates of "+startdate+" and "+finishdate+", stock price: "+stockPrice[j]); 
            }
            
            //First entry is null
            companies[j]=company;
        }
        
        //Finds change% and volatility
        for(int i=1;i<volatility.length;i++) {
            for(int j=1;j<change.length;j++) {
                change[j-1] = changeMethod(stockPrice[i],array[j][i]);
                //System.out.print(change[j-1]+" | ");
            }
            //System.out.println("");
            volatility[i-1]=standardDeviation(change);
        }
        
        //Prints volatility
        for(int i=1;i<volatility.length;i++) {
            if(volatility[i-1]<1.4) {
                System.out.print("\n"+companies[i]+": ");
                System.out.print(volatility[i-1]);
            }
        }
        
        System.out.println("End");
    }
    
    //Methods
    
    //computes standard deviation
    public static double standardDeviation(double[] changePercentage) {
        double mean=mean(changePercentage);
        double squareSum = 0;
        
        for(int i=0;i<changePercentage.length;i++)
            squareSum+=Math.pow(changePercentage[i]-mean,2);
        
        return Math.sqrt((squareSum)/(changePercentage.length-1));
    }
    
    //computes the mean
    public static double mean(double[] changePercentage) {
        double sum=0;
        
        for(int i=0;i<changePercentage.length;i++)
            sum+=changePercentage[i];
        
        return sum/changePercentage.length;
    }
    
    //computes the change percentage
    public static double changeMethod(double stockPrice,double changePercentage) {
        double price=stockPrice,change=changePercentage;
        return (price*change)/price;
    }
}
