package model;

public class ParseTest {



    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

//    @org.junit.Test
//    public void parseText() {
//        List<Document> documentList = new ArrayList<Document>();
//        Parse parse = new Parse();
//        List <Term> terms = null;
//        ReadFile readFile = new ReadFile();
//        documents = readFile.readFile("C:\\Users\\Tair\\Desktop\\corpus",documentList);
//        System.out.println(readFile.getNumOfDocs());
////        for (int i = 0; i < documentList.size(); i++) {
////           parse.parseText(documents.getDocuments().get(i));
////        }
////        for (int i = 0; i < terms.size(); i++) {
////            System.out.println(terms.get(i));
////        }










        //// % case
        //test Num 0 --> input:6% output: 6%
        /*
//
//        //test Num 0.1 --> input:6.1020% output: 6.102%
//        Document doc01 = new Document("0","12-05","1","6.1020%");
//        Parse parse01 = new Parse();
//        List<Term> t01 = parse01.parseText(doc01);
//        for (int i = 0; i < t01.size() ; i++) {
//            System.out.println(t01.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        //test Num 1 --> input: 6 % output: 6%
//        Document doc1 = new Document("1","12-05","1","6 %");
//        Parse parse1 = new Parse();
//        List<Term> t1 = parse1.parseText(doc1);
//        for (int i = 0; i < t1.size() ; i++) {
//            System.out.println(t1.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 2 --> input: 6.10 % output: 6.1%
//        Document doc2 = new Document("1","12-05","1","6.10 %");
//        Parse parse2 = new Parse();
//        List<Term> t2 = parse2.parseText(doc2);
//        for (int i = 0; i < t2.size() ; i++) {
//            System.out.println(t2.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 2.1 --> input: 6.10010101 % output: 6.1%
//        Document doc2_1 = new Document("1","12-05","1","6.10010101 %");
//        Parse parse2_1 = new Parse();
//        List<Term> t2_1 = parse2_1.parseText(doc2_1);
//        for (int i = 0; i < t2_1.size() ; i++) {
//            System.out.println(t2_1.get(i).toString());
//            System.out.println("\n");
//        }
//        // test Num 2.2 --> input: 6.10100101 % output: 6.101%
//        Document doc2_2 = new Document("1","12-05","1","6.10100101 %");
//        Parse parse2_2 = new Parse();
//        List<Term> t2_2 = parse2_2.parseText(doc2_2);
//        for (int i = 0; i < t2_2.size() ; i++) {
//            System.out.println(t2_2.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 3--> input: 10.6 percent output: 10.6%
//        Document doc3 = new Document("1","12-05","1","10.6 percent");
//        Parse parse3 = new Parse();
//        List<Term> t3 = parse3.parseText(doc3);
//        for (int i = 0; i < t3.size() ; i++) {
//            System.out.println(t3.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 4 --> input: 10 percent output: 10%
//        Document doc4 = new Document("1","12-05","1","10 percent");
//        Parse parse4 = new Parse();
//        List<Term> t4 = parse4.parseText(doc4);
//        for (int i = 0; i < t4.size() ; i++) {
//            System.out.println(t4.get(i).toString());
//            System.out.println("\n");
//        }
//
//        /*
//        // test Num 5 --> check this test after we finish the Class Parse
//        Document doc5 = new Document("1","12-05","1","10 percent %");
//        Parse parse5 = new Parse();
//        List<Term> t5 = parse5.parseText(doc5);
//        for (int i = 0; i < t5.size() ; i++) {
//            System.out.println(t5.get(i).toString());
//            System.out.println("\n");
//        }
//*/
//        /*
//        // test Num 6 input:"10 percentage" output:"10%"
//        Document doc6 = new Document("1","12-05","1","10 percentage");
//        Parse parse6 = new Parse();
//        List<Term> t6 = parse6.parseText(doc6);
//        for (int i = 0; i < t6.size() ; i++) {
//            System.out.println(t6.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 7 input:"10.901001 percentage" output:"10.901%"
//        Document doc7 = new Document("1","12-05","1","10.901001 percentage");
//        Parse parse7 = new Parse();
//        List<Term> t7 = parse7.parseText(doc7);
//        for (int i = 0; i < t7.size() ; i++) {
//            System.out.println(t7.get(i).toString());
//            System.out.println("\n");
//        }
//        //// DDMonth case
//
//        // test Num 8 input:"15 may" output:"05-15"
//        Document doc8 = new Document("1","12-05","1","15 may");
//        Parse parse8 = new Parse();
//        List<Term> t8 = parse8.parseText(doc8);
//        for (int i = 0; i < t8.size() ; i++) {
//            System.out.println(t8.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 9 input:"5 july" output:"07-05"
//        Document doc9 = new Document("1","12-05","1","5 july");
//        Parse parse9 = new Parse();
//        List<Term> t9 = parse9.parseText(doc9);
//        for (int i = 0; i < t9.size() ; i++) {
//            System.out.println(t9.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 10 input:"32 july" output:"32 july" --> for now it will be an endless loop because we did only the numbers
//       /* Document doc10 = new Document("1","12-05","1","32 july");
//        Parse parse10 = new Parse();
//        List<Term> t10 = parse10.parseText(doc10);
//        for (int i = 0; i < t10.size() ; i++) {
//            System.out.println(t10.get(i).toString());
//            System.out.println("\n");
//        }
//*/
//        // test Num 11 input:"3 mAy" output:"05-03"
//        Document doc11 = new Document("1","12-05","1","3 mAy");
//        Parse parse11 = new Parse();
//        List<Term> t11 = parse11.parseText(doc11);
//        for (int i = 0; i < t11.size() ; i++) {
//            System.out.println(t11.get(i).toString());
//            System.out.println("\n");
//        }
//        // case: Number without units
///*
//            //case: Number without units: K(Thousand) <Number<M(Million)
//        // test Num 12 input:"10,123" output:"10.123K"
//        Document doc12 = new Document("1","12-05","1","10,123");
//        Parse parse12 = new Parse();
//        List<Term> t12 = parse12.parseText(doc12);
//        for (int i = 0; i < t12.size() ; i++) {
//            System.out.println(t12.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 13 input:"10123" output:"10.123K"
//        Document doc13 = new Document("1","12-05","1","10123");
//        Parse parse13 = new Parse();
//        List<Term> t13 = parse13.parseText(doc13);
//        for (int i = 0; i < t13.size() ; i++) {
//            System.out.println(t13.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 14 input:"123 Thousand" output:"123K"
//        Document doc14 = new Document("1","12-05","1","123 Thousand");
//        Parse parse14 = new Parse();
//        List<Term> t14 = parse14.parseText(doc14);
//        for (int i = 0; i < t14.size() ; i++) {
//            System.out.println(t14.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 15 input:"123.14 Thousand" output:"123.14K"
//        Document doc15 = new Document("1","12-05","1","123.14 Thousand");
//        Parse parse15 = new Parse();
//        List<Term> t15 = parse15.parseText(doc15);
//        for (int i = 0; i < t15.size() ; i++) {
//            System.out.println(t15.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 16 input:"10,120.14508" output:"10.12K"
//        Document doc16 = new Document("1","12-05","1","10,120.14508");
//        Parse parse16 = new Parse();
//        List<Term> t16 = parse16.parseText(doc16);
//        for (int i = 0; i < t16.size() ; i++) {
//            System.out.println(t16.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 17 input:"100123.0012" output:"100.123K"
//        Document doc17 = new Document("1","12-05","1","100123.0012");
//        Parse parse17 = new Parse();
//        List<Term> t17 = parse17.parseText(doc17);
//        for (int i = 0; i < t17.size() ; i++) {
//            System.out.println(t17.get(i).toString());
//            System.out.println("\n");
//        }
//
//            //case: Number without units: M(Million) <Number<B(Billion)
//
//        // test Num 18 input:"10,123,123" output:"10.123M"
//        Document doc18 = new Document("1","12-05","1","10,123,123");
//        Parse parse18 = new Parse();
//        List<Term> t18 = parse18.parseText(doc18);
//        for (int i = 0; i < t18.size() ; i++) {
//            System.out.println(t18.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 19 input:"10000123" output:"10M"
//        Document doc19= new Document("1","12-05","1","10000123");
//        Parse parse19 = new Parse();
//        List<Term> t19 = parse19.parseText(doc19);
//        for (int i = 0; i < t19.size() ; i++) {
//            System.out.println(t19.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 20 input:"123 Million" output:"123M"
//        Document doc20 = new Document("1","12-05","1","123 Million");
//        Parse parse20 = new Parse();
//        List<Term> t20 = parse20.parseText(doc20);
//        for (int i = 0; i < t20.size() ; i++) {
//            System.out.println(t20.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 21 input:"123.14 MilLion" output:"123.14M"
//        Document doc21 = new Document("1","12-05","1","123.14 MilLion");
//        Parse parse21 = new Parse();
//        List<Term> t21 = parse21.parseText(doc21);
//        for (int i = 0; i < t21.size() ; i++) {
//            System.out.println(t21.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 22 input:"10,120,120.14508" output:"10.12M"
//        Document doc22 = new Document("1","12-05","1","10,120,120.14508");
//        Parse parse22 = new Parse();
//        List<Term> t22 = parse22.parseText(doc22);
//        for (int i = 0; i < t22.size() ; i++) {
//            System.out.println(t22.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 23 input:"100123123.0012" output:"100.123M"
//        Document doc23 = new Document("1","12-05","1","100123123.0012");
//        Parse parse23 = new Parse();
//        List<Term> t23 = parse23.parseText(doc23);
//        for (int i = 0; i < t23.size() ; i++) {
//            System.out.println(t23.get(i).toString());
//            System.out.println("\n");
//        }
//
//        //case: Number without units: Number>B(Billion)
//
//        // test Num 24 input:"10,123,123,000" output:"10.123B"
//        Document doc24 = new Document("1","12-05","1","10,123,123,000");
//        Parse parse24 = new Parse();
//        List<Term> t24 = parse24.parseText(doc24);
//        for (int i = 0; i < t24.size() ; i++) {
//            System.out.println(t24.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 25 input:"11000123000" output:"11B"
//        Document doc25= new Document("1","12-05","1","11000123000");
//        Parse parse25 = new Parse();
//        List<Term> t25 = parse25.parseText(doc25);
//        for (int i = 0; i < t25.size() ; i++) {
//            System.out.println(t25.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 26 input:"123 BillIon" output:"123B"
//        Document doc26 = new Document("1","12-05","1","123 BillIon");
//        Parse parse26 = new Parse();
//        List<Term> t26 = parse26.parseText(doc26);
//        for (int i = 0; i < t26.size() ; i++) {
//            System.out.println(t26.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 27 input:"123.14 BilLion" output:"123.14B"
//        Document doc27 = new Document("1","12-05","1","123.14 BilLion");
//        Parse parse27 = new Parse();
//        List<Term> t27 = parse27.parseText(doc27);
//        for (int i = 0; i < t27.size() ; i++) {
//            System.out.println(t27.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 28 input:"10,120,120,000.14508" output:"10.12B"
//        Document doc28 = new Document("1","12-05","1","10,120,120,000.14508");
//        Parse parse28 = new Parse();
//        List<Term> t28 = parse28.parseText(doc28);
//        for (int i = 0; i < t28.size() ; i++) {
//            System.out.println(t28.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 29 input:"100123120003.0012" output:"100.123B"
//        Document doc29 = new Document("1","12-05","1","100123120003.0012");
//        Parse parse29 = new Parse();
//        List<Term> t29 = parse29.parseText(doc29);
//        for (int i = 0; i < t29.size() ; i++) {
//            System.out.println(t29.get(i).toString());
//            System.out.println("\n");
//        }
//
//        //case: Number without units: K(Thousand) > Number
//
//        // test Num 30 input:"204" output:"204"
//        Document doc30 = new Document("1","12-05","1","204");
//        Parse parse30 = new Parse();
//        List<Term> t30 = parse30.parseText(doc30);
//        for (int i = 0; i < t30.size() ; i++) {
//            System.out.println(t30.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 31 input:"35.6001" output:"35.6"
//        Document doc31 = new Document("1","12-05","1","35.6001");
//        Parse parse31 = new Parse();
//        List<Term> t31 = parse31.parseText(doc31);
//        for (int i = 0; i < t31.size() ; i++) {
//            System.out.println(t31.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 32 input:"24 3/44444" output:"24 3/44444"
//        Document doc32 = new Document("1","12-05","1","24 3/44444");
//        Parse parse32 = new Parse();
//        List<Term> t32 = parse32.parseText(doc32);
//        for (int i = 0; i < t32.size() ; i++) {
//            System.out.println(t32.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 33 input:"204.1 3/2" output:"204.1 3/2"
//        Document doc33 = new Document("1","12-05","1","204.1 3/2");
//        Parse parse33 = new Parse();
//        List<Term> t33 = parse33.parseText(doc33);
//        for (int i = 0; i < t33.size() ; i++) {
//            System.out.println(t33.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 34 input:"3333/21233" output:"3333/21233"
//        Document doc34 = new Document("1","12-05","1","3333/21233");
//        Parse parse34 = new Parse();
//        List<Term> t34 = parse34.parseText(doc34);
//        for (int i = 0; i < t34.size() ; i++) {
//            System.out.println(t34.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        /// case: $ $ $ $ $ $
//                /// - then million
//        // test Num 35 input:"1.7301 Dollars" output:"1.73 Dollars"
//        Document doc35 = new Document("1","12-05","1","1.7301 Dollars");
//        Parse parse35 = new Parse();
//        List<Term> t35 = parse35.parseText(doc35);
//        for (int i = 0; i < t35.size() ; i++) {
//            System.out.println(t35.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 36 input:"999999 Dollars" output:"999999 Dollars"
//        Document doc36 = new Document("1","12-05","1","999999 Dollars");
//        Parse parse36 = new Parse();
//        List<Term> t36 = parse36.parseText(doc36);
//        for (int i = 0; i < t36.size() ; i++) {
//            System.out.println(t36.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 37 input:"999,999 Dollars" output:"999999 Dollars"
//        Document doc37 = new Document("1","12-05","1","999,999 Dollars");
//        Parse parse37 = new Parse();
//        List<Term> t37 = parse37.parseText(doc37);
//        for (int i = 0; i < t37.size() ; i++) {
//            System.out.println(t37.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 38 input:"22 3/4 Dollars" output:"22 3/4 Dollars"
//        Document doc38 = new Document("1","12-05","1","22 3/4 Dollars");
//        Parse parse38 = new Parse();
//        List<Term> t38 = parse38.parseText(doc38);
//        for (int i = 0; i < t38.size() ; i++) {
//            System.out.println(t38.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 39 input:"3/4 Dollars" output:"3/4 Dollars"
//        Document doc39 = new Document("1","12-05","1","3/4 Dollars");
//        Parse parse39 = new Parse();
//        List<Term> t39 = parse39.parseText(doc39);
//        for (int i = 0; i < t39.size() ; i++) {
//            System.out.println(t39.get(i).toString());
//            System.out.println("\n");
//        }
//         */
/////////from here
//        // test Num 40 input:"$450,000" output:"450000 Dollars"
//        Document doc40 = new Document("1","12-05","1","$450,000");
//        Parse parse40 = new Parse();
//        List<Term> t40 = parse40.parseText(doc40);
//        for (int i = 0; i < t40.size() ; i++) {
//            System.out.println(t40.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 41 input:"$450,000.1401" output:"450000.14 Dollars"
//        Document doc41 = new Document("1","12-05","1","$450,000.1401");
//        Parse parse41 = new Parse();
//        List<Term> t41 = parse41.parseText(doc41);
//        for (int i = 0; i < t41.size() ; i++) {
//            System.out.println(t41.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 42 input:"$450,000 1/2" output:"450000 Dollars 1/2"
//        Document doc42 = new Document("1","12-05","1","$450,000 1/2");
//        Parse parse42 = new Parse();
//        List<Term> t42 = parse42.parseText(doc42);
//        for (int i = 0; i < t42.size() ; i++) {
//            System.out.println(t42.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 44 input:"1.2 3/4 Dollars" output:"1.2 3/4 Dollars"
//        Document doc44 = new Document("1","12-05","1","1.2 3/4 Dollars");
//        Parse parse44 = new Parse();
//        List<Term> t44 = parse44.parseText(doc44);
//        for (int i = 0; i < t44.size() ; i++) {
//            System.out.println(t44.get(i).toString());
//            System.out.println("\n");
//        }
//
//        /// case: $ $ $ $ $ $
//                  /// + then million
//        // test Num 45 input:"1,000,000 Dollars" output:"1 M Dollars"
//        Document doc45 = new Document("1","12-05","1","1,000,000 Dollars");
//        Parse parse45 = new Parse();
//        List<Term> t45 = parse45.parseText(doc45);
//        for (int i = 0; i < t45.size() ; i++) {
//            System.out.println(t45.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 46 input:"9999999 Dollars" output:"9.99 M Dollars"
//        Document doc46 = new Document("1","12-05","1","9999999 Dollars");
//        Parse parse46 = new Parse();
//        List<Term> t46 = parse46.parseText(doc46);
//        for (int i = 0; i < t46.size() ; i++) {
//            System.out.println(t46.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 47 input:"10000000.210 Dollars" output:"10 M Dollars"
//        Document doc47 = new Document("1","12-05","1","10000000.210 Dollars");
//        Parse parse47 = new Parse();
//        List<Term> t47 = parse47.parseText(doc47);
//        for (int i = 0; i < t47.size() ; i++) {
//            System.out.println(t47.get(i).toString());
//            System.out.println("\n");
//        }
//
//        //test Num 48 input:"10000000 1/2 Dollars" output:"10 M 1/2 Dollars"
//        Document doc48 = new Document("1","12-05","1","10000000 1/2 Dollars");
//        Parse parse48 = new Parse();
//        List<Term> t48 = parse48.parseText(doc48);
//        for (int i = 0; i < t48.size() ; i++) {
//            System.out.println(t48.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 49 input:"100 m Dollars" output:"100 M Dollars"
//        Document doc49 = new Document("1","12-05","1","100 m Dollars");
//        Parse parse49 = new Parse();
//        List<Term> t49 = parse49.parseText(doc49);
//        for (int i = 0; i < t49.size() ; i++) {
//            System.out.println(t49.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 50 input:"100 M Dollars" output:"100 M Dollars"
//        Document doc50 = new Document("1","12-05","1","100 M Dollars");
//        Parse parse50 = new Parse();
//        List<Term> t50 = parse50.parseText(doc50);
//        for (int i = 0; i < t50.size() ; i++) {
//            System.out.println(t50.get(i).toString());
//            System.out.println("\n");
//        }
//        // test Num 51 input:"100.120 M Dollars" output:"100.12 M Dollars"
//        Document doc51 = new Document("1","12-05","1","100.120 M Dollars");
//        Parse parse51 = new Parse();
//        List<Term> t51 = parse51.parseText(doc51);
//        for (int i = 0; i < t51.size() ; i++) {
//            System.out.println(t51.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 52 input:"100 bN Dollars" output:"100000 M Dollars"
//        Document doc52 = new Document("1","12-05","1","100 bN Dollars");
//        Parse parse52 = new Parse();
//        List<Term> t52 = parse52.parseText(doc52);
//        for (int i = 0; i < t52.size() ; i++) {
//            System.out.println(t52.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 53 input:"9.001 bN Dollars" output:"9901 M Dollars"
//        Document doc53 = new Document("1","12-05","1","9.001 bN Dollars");
//        Parse parse53 = new Parse();
//        List<Term> t53 = parse53.parseText(doc53);
//        for (int i = 0; i < t53.size() ; i++) {
//            System.out.println(t53.get(i).toString());
//            System.out.println("\n");
//        }
//        // test Num 54 input:"100.120 bn Dollars" output:"100120 M Dollars"
//        Document doc54 = new Document("1","12-05","1","100.120 bn Dollars");
//        Parse parse54 = new Parse();
//        List<Term> t54 = parse54.parseText(doc54);
//        for (int i = 0; i < t54.size() ; i++) {
//            System.out.println(t54.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 55 input:"100.120 billion U.S. dollars" output:"100120 M Dollars"
//        Document doc55 = new Document("1","12-05","1","100.120 billion U.S. dollars");
//        Parse parse55 = new Parse();
//        List<Term> t55 = parse55.parseText(doc55);
//        for (int i = 0; i < t55.size() ; i++) {
//            System.out.println(t55.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 56 input:"100.120 million U.S. dollars" output:"100.12 M Dollars"
//        Document doc56 = new Document("1","12-05","1","100.120 million U.S. dollars");
//        Parse parse56 = new Parse();
//        List<Term> t56 = parse56.parseText(doc56);
//        for (int i = 0; i < t56.size() ; i++) {
//            System.out.println(t56.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 57 input:"999 million U.S. dollars" output:"999 M Dollars"
//        Document doc57 = new Document("1","12-05","1","999 million U.S. dollars");
//        Parse parse57 = new Parse();
//        List<Term> t57 = parse57.parseText(doc57);
//        for (int i = 0; i < t57.size() ; i++) {
//            System.out.println(t57.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 58 input:"999.9999999 tRIllion U.s. dolLars" output:"999999999.9 M Dollars"
//        Document doc58 = new Document("1","12-05","1","999.9999999 tRIllion U.s. dolLars");
//        Parse parse58 = new Parse();
//        List<Term> t58 = parse58.parseText(doc58);
//        for (int i = 0; i < t58.size() ; i++) {
//            System.out.println(t58.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//
////FRAC :(
//
//        // test Num 59 input:"1,000,000 1/4 Dollars" output:"1 M  1/4 Dollars"
//        Document doc59 = new Document("1","12-05","1","1,000,000 1/4 Dollars");
//        Parse parse59 = new Parse();
//        List<Term> t59 = parse59.parseText(doc59);
//        for (int i = 0; i < t59.size() ; i++) {
//            System.out.println(t59.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 60 input:"999999 1/4 Dollars" output:"999999 1/4 Dollars"
//        Document doc60 = new Document("1","12-05","1","999999 1/4 Dollars");
//        Parse parse60 = new Parse();
//        List<Term> t60 = parse60.parseText(doc60);
//        for (int i = 0; i < t60.size() ; i++) {
//            System.out.println(t60.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 61 input:"100 1/4 m Dollars" output:"100 1/4 M Dollars"
//        Document doc61 = new Document("1","12-05","1","100 1/4 m Dollars");
//        Parse parse61 = new Parse();
//        List<Term> t61 = parse61.parseText(doc61);
//        for (int i = 0; i < t61.size() ; i++) {
//            System.out.println(t61.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 62 input:"100.1 1/4 M Dollars" output:"100.1 1/4 M Dollars"
//        Document doc62 = new Document("1","12-05","1","100.1 1/4 M Dollars");
//        Parse parse62 = new Parse();
//        List<Term> t62 = parse62.parseText(doc62);
//        for (int i = 0; i < t62.size() ; i++) {
//            System.out.println(t62.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 63 input:"1/4 bN Dollars" output:"1/4 BN Dollars"
//        Document doc63 = new Document("1","12-05","1","1/4 bN Dollars");
//        Parse parse63 = new Parse();
//        List<Term> t63 = parse63.parseText(doc63);
//        for (int i = 0; i < t63.size() ; i++) {
//            System.out.println(t63.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 64 input:"1/4 Billion U.S. dollars" output:"1/4 Billion U.S. dollars"
//        Document doc64 = new Document("1","12-05","1","1/4 billion U.S. dollars");
//        Parse parse64 = new Parse();
//        List<Term> t64 = parse64.parseText(doc64);
//        for (int i = 0; i <   t64.size() ; i++) {
//            System.out.println(t64.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//
//
//        // test Num 65 input:"$459,999,999" output:"459.999 M Dollars"
//        Document doc65 = new Document("1","12-05","1","$459,999,999");
//        Parse parse65 = new Parse();
//        List<Term> t65 = parse65.parseText(doc65);
//        for (int i = 0; i < t65.size() ; i++) {
//            System.out.println(t65.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 66 input:"$450,000.90001 million" output:"450000.9 Dollars million"
//        Document doc66 = new Document("1","12-05","1","$450,000.90001 million");
//        Parse parse66 = new Parse();
//        List<Term> t66 = parse66.parseText(doc66);
//        for (int i = 0; i < t66.size() ; i++) {
//            System.out.println(t66.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 67 input:"$459,999,999.9" output:"459.999 M Dollars"
//        Document doc67 = new Document("1","12-05","1","$459,999,999.9");
//        Parse parse67 = new Parse();
//        List<Term> t67 = parse67.parseText(doc67);
//        for (int i = 0; i < t67.size() ; i++) {
//            System.out.println(t67.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 68 input:"$4,450,000.90001 BiLlion" output:"4.45 M Dollars BiLlion"
//        Document doc68 = new Document("1","12-05","1","$4,450,000.90001 BiLlion");
//        Parse parse68 = new Parse();
//        List<Term> t68 = parse68.parseText(doc68);
//        for (int i = 0; i < t68.size() ; i++) {
//            System.out.println(t68.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        //test Num 2
//        Document doc2 = new Document("2","12-05","1","123 Thousand");
//        Parse parse2 = new Parse();
//        List<Term> t2 = parse2.parseText(doc2);
//        for (int i = 0; i < t2.size() ; i++) {
//            System.out.println(t2.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//
//
//        //test Num 1
//        Document doc = new Document("1","12-05","1","10,123");
//        Parse parse = new Parse();
//        List<Term> t = parse.parseText(doc);
//        for (int i = 0; i < t.size() ; i++) {
//            System.out.println(t.get(i).toString());
//            System.out.println("\n");
//        }


    @org.junit.Test
    public void parseText() {

    }


        //
//
//        /*
//        //// % case
//        //test Num 0 --> input:6% output: 6%
//        Document doc0 = new Document("0","12-05","1","6%");
//        Parse parse0 = new Parse();
//        List<Term> t0 = parse0.parseText(doc0);
//        String str ="";
//        for (int i = 0; i < t0.size() ; i++) {
//            str=str+t0.get(i).toString();
//            System.out.println("\n");
//        }
//
//        //test Num 0.1 --> input:6.1020% output: 6.102%
//        Document doc01 = new Document("0","12-05","1","6.1020%");
//        Parse parse01 = new Parse();
//        List<Term> t01 = parse01.parseText(doc01);
//        for (int i = 0; i < t01.size() ; i++) {
//            System.out.println(t01.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        //test Num 1 --> input: 6 % output: 6%
//        Document doc1 = new Document("1","12-05","1","6 %");
//        Parse parse1 = new Parse();
//        List<Term> t1 = parse1.parseText(doc1);
//        for (int i = 0; i < t1.size() ; i++) {
//            System.out.println(t1.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 2 --> input: 6.10 % output: 6.1%
//        Document doc2 = new Document("1","12-05","1","6.10 %");
//        Parse parse2 = new Parse();
//        List<Term> t2 = parse2.parseText(doc2);
//        for (int i = 0; i < t2.size() ; i++) {
//            System.out.println(t2.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 2.1 --> input: 6.10010101 % output: 6.1%
//        Document doc2_1 = new Document("1","12-05","1","6.10010101 %");
//        Parse parse2_1 = new Parse();
//        List<Term> t2_1 = parse2_1.parseText(doc2_1);
//        for (int i = 0; i < t2_1.size() ; i++) {
//            System.out.println(t2_1.get(i).toString());
//            System.out.println("\n");
//        }
//        // test Num 2.2 --> input: 6.10100101 % output: 6.101%
//        Document doc2_2 = new Document("1","12-05","1","6.10100101 %");
//        Parse parse2_2 = new Parse();
//        List<Term> t2_2 = parse2_2.parseText(doc2_2);
//        for (int i = 0; i < t2_2.size() ; i++) {
//            System.out.println(t2_2.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 3--> input: 10.6 percent output: 10.6%
//        Document doc3 = new Document("1","12-05","1","10.6 percent");
//        Parse parse3 = new Parse();
//        List<Term> t3 = parse3.parseText(doc3);
//        for (int i = 0; i < t3.size() ; i++) {
//            System.out.println(t3.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 4 --> input: 10 percent output: 10%
//        Document doc4 = new Document("1","12-05","1","10 percent");
//        Parse parse4 = new Parse();
//        List<Term> t4 = parse4.parseText(doc4);
//        for (int i = 0; i < t4.size() ; i++) {
//            System.out.println(t4.get(i).toString());
//            System.out.println("\n");
//        }
//
//        /*
//        // test Num 5 --> check this test after we finish the Class Parse
//        Document doc5 = new Document("1","12-05","1","10 percent %");
//        Parse parse5 = new Parse();
//        List<Term> t5 = parse5.parseText(doc5);
//        for (int i = 0; i < t5.size() ; i++) {
//            System.out.println(t5.get(i).toString());
//            System.out.println("\n");
//        }
//*/
//        /*
//        // test Num 6 input:"10 percentage" output:"10%"
//        Document doc6 = new Document("1","12-05","1","10 percentage");
//        Parse parse6 = new Parse();
//        List<Term> t6 = parse6.parseText(doc6);
//        for (int i = 0; i < t6.size() ; i++) {
//            System.out.println(t6.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 7 input:"10.901001 percentage" output:"10.901%"
//        Document doc7 = new Document("1","12-05","1","10.901001 percentage");
//        Parse parse7 = new Parse();
//        List<Term> t7 = parse7.parseText(doc7);
//        for (int i = 0; i < t7.size() ; i++) {
//            System.out.println(t7.get(i).toString());
//            System.out.println("\n");
//        }
//        //// DDMonth case
//
//        // test Num 8 input:"15 may" output:"05-15"
//        Document doc8 = new Document("1","12-05","1","15 may");
//        Parse parse8 = new Parse();
//        List<Term> t8 = parse8.parseText(doc8);
//        for (int i = 0; i < t8.size() ; i++) {
//            System.out.println(t8.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 9 input:"5 july" output:"07-05"
//        Document doc9 = new Document("1","12-05","1","5 july");
//        Parse parse9 = new Parse();
//        List<Term> t9 = parse9.parseText(doc9);
//        for (int i = 0; i < t9.size() ; i++) {
//            System.out.println(t9.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 10 input:"32 july" output:"32 july" --> for now it will be an endless loop because we did only the numbers
//       /* Document doc10 = new Document("1","12-05","1","32 july");
//        Parse parse10 = new Parse();
//        List<Term> t10 = parse10.parseText(doc10);
//        for (int i = 0; i < t10.size() ; i++) {
//            System.out.println(t10.get(i).toString());
//            System.out.println("\n");
//        }
//*/
//        // test Num 11 input:"3 mAy" output:"05-03"
//        Document doc11 = new Document("1","12-05","1","3 mAy");
//        Parse parse11 = new Parse();
//        List<Term> t11 = parse11.parseText(doc11);
//        for (int i = 0; i < t11.size() ; i++) {
//            System.out.println(t11.get(i).toString());
//            System.out.println("\n");
//        }
//        // case: Number without units
///*
//            //case: Number without units: K(Thousand) <Number<M(Million)
//        // test Num 12 input:"10,123" output:"10.123K"
//        Document doc12 = new Document("1","12-05","1","10,123");
//        Parse parse12 = new Parse();
//        List<Term> t12 = parse12.parseText(doc12);
//        for (int i = 0; i < t12.size() ; i++) {
//            System.out.println(t12.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 13 input:"10123" output:"10.123K"
//        Document doc13 = new Document("1","12-05","1","10123");
//        Parse parse13 = new Parse();
//        List<Term> t13 = parse13.parseText(doc13);
//        for (int i = 0; i < t13.size() ; i++) {
//            System.out.println(t13.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 14 input:"123 Thousand" output:"123K"
//        Document doc14 = new Document("1","12-05","1","123 Thousand");
//        Parse parse14 = new Parse();
//        List<Term> t14 = parse14.parseText(doc14);
//        for (int i = 0; i < t14.size() ; i++) {
//            System.out.println(t14.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 15 input:"123.14 Thousand" output:"123.14K"
//        Document doc15 = new Document("1","12-05","1","123.14 Thousand");
//        Parse parse15 = new Parse();
//        List<Term> t15 = parse15.parseText(doc15);
//        for (int i = 0; i < t15.size() ; i++) {
//            System.out.println(t15.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 16 input:"10,120.14508" output:"10.12K"
//        Document doc16 = new Document("1","12-05","1","10,120.14508");
//        Parse parse16 = new Parse();
//        List<Term> t16 = parse16.parseText(doc16);
//        for (int i = 0; i < t16.size() ; i++) {
//            System.out.println(t16.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 17 input:"100123.0012" output:"100.123K"
//        Document doc17 = new Document("1","12-05","1","100123.0012");
//        Parse parse17 = new Parse();
//        List<Term> t17 = parse17.parseText(doc17);
//        for (int i = 0; i < t17.size() ; i++) {
//            System.out.println(t17.get(i).toString());
//            System.out.println("\n");
//        }
//
//            //case: Number without units: M(Million) <Number<B(Billion)
//
//        // test Num 18 input:"10,123,123" output:"10.123M"
//        Document doc18 = new Document("1","12-05","1","10,123,123");
//        Parse parse18 = new Parse();
//        List<Term> t18 = parse18.parseText(doc18);
//        for (int i = 0; i < t18.size() ; i++) {
//            System.out.println(t18.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 19 input:"10000123" output:"10M"
//        Document doc19= new Document("1","12-05","1","10000123");
//        Parse parse19 = new Parse();
//        List<Term> t19 = parse19.parseText(doc19);
//        for (int i = 0; i < t19.size() ; i++) {
//            System.out.println(t19.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 20 input:"123 Million" output:"123M"
//        Document doc20 = new Document("1","12-05","1","123 Million");
//        Parse parse20 = new Parse();
//        List<Term> t20 = parse20.parseText(doc20);
//        for (int i = 0; i < t20.size() ; i++) {
//            System.out.println(t20.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 21 input:"123.14 MilLion" output:"123.14M"
//        Document doc21 = new Document("1","12-05","1","123.14 MilLion");
//        Parse parse21 = new Parse();
//        List<Term> t21 = parse21.parseText(doc21);
//        for (int i = 0; i < t21.size() ; i++) {
//            System.out.println(t21.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 22 input:"10,120,120.14508" output:"10.12M"
//        Document doc22 = new Document("1","12-05","1","10,120,120.14508");
//        Parse parse22 = new Parse();
//        List<Term> t22 = parse22.parseText(doc22);
//        for (int i = 0; i < t22.size() ; i++) {
//            System.out.println(t22.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 23 input:"100123123.0012" output:"100.123M"
//        Document doc23 = new Document("1","12-05","1","100123123.0012");
//        Parse parse23 = new Parse();
//        List<Term> t23 = parse23.parseText(doc23);
//        for (int i = 0; i < t23.size() ; i++) {
//            System.out.println(t23.get(i).toString());
//            System.out.println("\n");
//        }
//
//        //case: Number without units: Number>B(Billion)
//
//        // test Num 24 input:"10,123,123,000" output:"10.123B"
//        Document doc24 = new Document("1","12-05","1","10,123,123,000");
//        Parse parse24 = new Parse();
//        List<Term> t24 = parse24.parseText(doc24);
//        for (int i = 0; i < t24.size() ; i++) {
//            System.out.println(t24.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 25 input:"11000123000" output:"11B"
//        Document doc25= new Document("1","12-05","1","11000123000");
//        Parse parse25 = new Parse();
//        List<Term> t25 = parse25.parseText(doc25);
//        for (int i = 0; i < t25.size() ; i++) {
//            System.out.println(t25.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 26 input:"123 BillIon" output:"123B"
//        Document doc26 = new Document("1","12-05","1","123 BillIon");
//        Parse parse26 = new Parse();
//        List<Term> t26 = parse26.parseText(doc26);
//        for (int i = 0; i < t26.size() ; i++) {
//            System.out.println(t26.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 27 input:"123.14 BilLion" output:"123.14B"
//        Document doc27 = new Document("1","12-05","1","123.14 BilLion");
//        Parse parse27 = new Parse();
//        List<Term> t27 = parse27.parseText(doc27);
//        for (int i = 0; i < t27.size() ; i++) {
//            System.out.println(t27.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 28 input:"10,120,120,000.14508" output:"10.12B"
//        Document doc28 = new Document("1","12-05","1","10,120,120,000.14508");
//        Parse parse28 = new Parse();
//        List<Term> t28 = parse28.parseText(doc28);
//        for (int i = 0; i < t28.size() ; i++) {
//            System.out.println(t28.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 29 input:"100123120003.0012" output:"100.123B"
//        Document doc29 = new Document("1","12-05","1","100123120003.0012");
//        Parse parse29 = new Parse();
//        List<Term> t29 = parse29.parseText(doc29);
//        for (int i = 0; i < t29.size() ; i++) {
//            System.out.println(t29.get(i).toString());
//            System.out.println("\n");
//        }
//
//        //case: Number without units: K(Thousand) > Number
//
//        // test Num 30 input:"204" output:"204"
//        Document doc30 = new Document("1","12-05","1","204");
//        Parse parse30 = new Parse();
//        List<Term> t30 = parse30.parseText(doc30);
//        for (int i = 0; i < t30.size() ; i++) {
//            System.out.println(t30.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 31 input:"35.6001" output:"35.6"
//        Document doc31 = new Document("1","12-05","1","35.6001");
//        Parse parse31 = new Parse();
//        List<Term> t31 = parse31.parseText(doc31);
//        for (int i = 0; i < t31.size() ; i++) {
//            System.out.println(t31.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 32 input:"24 3/44444" output:"24 3/44444"
//        Document doc32 = new Document("1","12-05","1","24 3/44444");
//        Parse parse32 = new Parse();
//        List<Term> t32 = parse32.parseText(doc32);
//        for (int i = 0; i < t32.size() ; i++) {
//            System.out.println(t32.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 33 input:"204.1 3/2" output:"204.1 3/2"
//        Document doc33 = new Document("1","12-05","1","204.1 3/2");
//        Parse parse33 = new Parse();
//        List<Term> t33 = parse33.parseText(doc33);
//        for (int i = 0; i < t33.size() ; i++) {
//            System.out.println(t33.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 34 input:"3333/21233" output:"3333/21233"
//        Document doc34 = new Document("1","12-05","1","3333/21233");
//        Parse parse34 = new Parse();
//        List<Term> t34 = parse34.parseText(doc34);
//        for (int i = 0; i < t34.size() ; i++) {
//            System.out.println(t34.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        /// case: $ $ $ $ $ $
//                /// - then million
//        // test Num 35 input:"1.7301 Dollars" output:"1.73 Dollars"
//        Document doc35 = new Document("1","12-05","1","1.7301 Dollars");
//        Parse parse35 = new Parse();
//        List<Term> t35 = parse35.parseText(doc35);
//        for (int i = 0; i < t35.size() ; i++) {
//            System.out.println(t35.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 36 input:"999999 Dollars" output:"999999 Dollars"
//        Document doc36 = new Document("1","12-05","1","999999 Dollars");
//        Parse parse36 = new Parse();
//        List<Term> t36 = parse36.parseText(doc36);
//        for (int i = 0; i < t36.size() ; i++) {
//            System.out.println(t36.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 37 input:"999,999 Dollars" output:"999999 Dollars"
//        Document doc37 = new Document("1","12-05","1","999,999 Dollars");
//        Parse parse37 = new Parse();
//        List<Term> t37 = parse37.parseText(doc37);
//        for (int i = 0; i < t37.size() ; i++) {
//            System.out.println(t37.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 38 input:"22 3/4 Dollars" output:"22 3/4 Dollars"
//        Document doc38 = new Document("1","12-05","1","22 3/4 Dollars");
//        Parse parse38 = new Parse();
//        List<Term> t38 = parse38.parseText(doc38);
//        for (int i = 0; i < t38.size() ; i++) {
//            System.out.println(t38.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 39 input:"3/4 Dollars" output:"3/4 Dollars"
//        Document doc39 = new Document("1","12-05","1","3/4 Dollars");
//        Parse parse39 = new Parse();
//        List<Term> t39 = parse39.parseText(doc39);
//        for (int i = 0; i < t39.size() ; i++) {
//            System.out.println(t39.get(i).toString());
//            System.out.println("\n");
//        }
//         */
/////////from here
//        // test Num 40 input:"$450,000" output:"450000 Dollars"
//        Document doc40 = new Document("1","12-05","1","$450,000");
//        Parse parse40 = new Parse();
//        List<Term> t40 = parse40.parseText(doc40);
//        for (int i = 0; i < t40.size() ; i++) {
//            System.out.println(t40.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 41 input:"$450,000.1401" output:"450000.14 Dollars"
//        Document doc41 = new Document("1","12-05","1","$450,000.1401");
//        Parse parse41 = new Parse();
//        List<Term> t41 = parse41.parseText(doc41);
//        for (int i = 0; i < t41.size() ; i++) {
//            System.out.println(t41.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 42 input:"$450,000 1/2" output:"450000 Dollars 1/2"
//        Document doc42 = new Document("1","12-05","1","$450,000 1/2");
//        Parse parse42 = new Parse();
//        List<Term> t42 = parse42.parseText(doc42);
//        for (int i = 0; i < t42.size() ; i++) {
//            System.out.println(t42.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 44 input:"1.2 3/4 Dollars" output:"1.2 3/4 Dollars"
//        Document doc44 = new Document("1","12-05","1","1.2 3/4 Dollars");
//        Parse parse44 = new Parse();
//        List<Term> t44 = parse44.parseText(doc44);
//        for (int i = 0; i < t44.size() ; i++) {
//            System.out.println(t44.get(i).toString());
//            System.out.println("\n");
//        }
//
//        /// case: $ $ $ $ $ $
//                  /// + then million
//        // test Num 45 input:"1,000,000 Dollars" output:"1 M Dollars"
//        Document doc45 = new Document("1","12-05","1","1,000,000 Dollars");
//        Parse parse45 = new Parse();
//        List<Term> t45 = parse45.parseText(doc45);
//        for (int i = 0; i < t45.size() ; i++) {
//            System.out.println(t45.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 46 input:"9999999 Dollars" output:"9.99 M Dollars"
//        Document doc46 = new Document("1","12-05","1","9999999 Dollars");
//        Parse parse46 = new Parse();
//        List<Term> t46 = parse46.parseText(doc46);
//        for (int i = 0; i < t46.size() ; i++) {
//            System.out.println(t46.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 47 input:"10000000.210 Dollars" output:"10 M Dollars"
//        Document doc47 = new Document("1","12-05","1","10000000.210 Dollars");
//        Parse parse47 = new Parse();
//        List<Term> t47 = parse47.parseText(doc47);
//        for (int i = 0; i < t47.size() ; i++) {
//            System.out.println(t47.get(i).toString());
//            System.out.println("\n");
//        }
//
//        //test Num 48 input:"10000000 1/2 Dollars" output:"10 M 1/2 Dollars"
//        Document doc48 = new Document("1","12-05","1","10000000 1/2 Dollars");
//        Parse parse48 = new Parse();
//        List<Term> t48 = parse48.parseText(doc48);
//        for (int i = 0; i < t48.size() ; i++) {
//            System.out.println(t48.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 49 input:"100 m Dollars" output:"100 M Dollars"
//        Document doc49 = new Document("1","12-05","1","100 m Dollars");
//        Parse parse49 = new Parse();
//        List<Term> t49 = parse49.parseText(doc49);
//        for (int i = 0; i < t49.size() ; i++) {
//            System.out.println(t49.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 50 input:"100 M Dollars" output:"100 M Dollars"
//        Document doc50 = new Document("1","12-05","1","100 M Dollars");
//        Parse parse50 = new Parse();
//        List<Term> t50 = parse50.parseText(doc50);
//        for (int i = 0; i < t50.size() ; i++) {
//            System.out.println(t50.get(i).toString());
//            System.out.println("\n");
//        }
//        // test Num 51 input:"100.120 M Dollars" output:"100.12 M Dollars"
//        Document doc51 = new Document("1","12-05","1","100.120 M Dollars");
//        Parse parse51 = new Parse();
//        List<Term> t51 = parse51.parseText(doc51);
//        for (int i = 0; i < t51.size() ; i++) {
//            System.out.println(t51.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 52 input:"100 bN Dollars" output:"100000 M Dollars"
//        Document doc52 = new Document("1","12-05","1","100 bN Dollars");
//        Parse parse52 = new Parse();
//        List<Term> t52 = parse52.parseText(doc52);
//        for (int i = 0; i < t52.size() ; i++) {
//            System.out.println(t52.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 53 input:"9.001 bN Dollars" output:"9901 M Dollars"
//        Document doc53 = new Document("1","12-05","1","9.001 bN Dollars");
//        Parse parse53 = new Parse();
//        List<Term> t53 = parse53.parseText(doc53);
//        for (int i = 0; i < t53.size() ; i++) {
//            System.out.println(t53.get(i).toString());
//            System.out.println("\n");
//        }
//        // test Num 54 input:"100.120 bn Dollars" output:"100120 M Dollars"
//        Document doc54 = new Document("1","12-05","1","100.120 bn Dollars");
//        Parse parse54 = new Parse();
//        List<Term> t54 = parse54.parseText(doc54);
//        for (int i = 0; i < t54.size() ; i++) {
//            System.out.println(t54.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 55 input:"100.120 billion U.S. dollars" output:"100120 M Dollars"
//        Document doc55 = new Document("1","12-05","1","100.120 billion U.S. dollars");
//        Parse parse55 = new Parse();
//        List<Term> t55 = parse55.parseText(doc55);
//        for (int i = 0; i < t55.size() ; i++) {
//            System.out.println(t55.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 56 input:"100.120 million U.S. dollars" output:"100.12 M Dollars"
//        Document doc56 = new Document("1","12-05","1","100.120 million U.S. dollars");
//        Parse parse56 = new Parse();
//        List<Term> t56 = parse56.parseText(doc56);
//        for (int i = 0; i < t56.size() ; i++) {
//            System.out.println(t56.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 57 input:"999 million U.S. dollars" output:"999 M Dollars"
//        Document doc57 = new Document("1","12-05","1","999 million U.S. dollars");
//        Parse parse57 = new Parse();
//        List<Term> t57 = parse57.parseText(doc57);
//        for (int i = 0; i < t57.size() ; i++) {
//            System.out.println(t57.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 58 input:"999.9999999 tRIllion U.s. dolLars" output:"999999999.9 M Dollars"
//        Document doc58 = new Document("1","12-05","1","999.9999999 tRIllion U.s. dolLars");
//        Parse parse58 = new Parse();
//        List<Term> t58 = parse58.parseText(doc58);
//        for (int i = 0; i < t58.size() ; i++) {
//            System.out.println(t58.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//
////FRAC :(
//
//        // test Num 59 input:"1,000,000 1/4 Dollars" output:"1 M  1/4 Dollars"
//        Document doc59 = new Document("1","12-05","1","1,000,000 1/4 Dollars");
//        Parse parse59 = new Parse();
//        List<Term> t59 = parse59.parseText(doc59);
//        for (int i = 0; i < t59.size() ; i++) {
//            System.out.println(t59.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 60 input:"999999 1/4 Dollars" output:"999999 1/4 Dollars"
//        Document doc60 = new Document("1","12-05","1","999999 1/4 Dollars");
//        Parse parse60 = new Parse();
//        List<Term> t60 = parse60.parseText(doc60);
//        for (int i = 0; i < t60.size() ; i++) {
//            System.out.println(t60.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 61 input:"100 1/4 m Dollars" output:"100 1/4 M Dollars"
//        Document doc61 = new Document("1","12-05","1","100 1/4 m Dollars");
//        Parse parse61 = new Parse();
//        List<Term> t61 = parse61.parseText(doc61);
//        for (int i = 0; i < t61.size() ; i++) {
//            System.out.println(t61.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 62 input:"100.1 1/4 M Dollars" output:"100.1 1/4 M Dollars"
//        Document doc62 = new Document("1","12-05","1","100.1 1/4 M Dollars");
//        Parse parse62 = new Parse();
//        List<Term> t62 = parse62.parseText(doc62);
//        for (int i = 0; i < t62.size() ; i++) {
//            System.out.println(t62.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 63 input:"1/4 bN Dollars" output:"1/4 BN Dollars"
//        Document doc63 = new Document("1","12-05","1","1/4 bN Dollars");
//        Parse parse63 = new Parse();
//        List<Term> t63 = parse63.parseText(doc63);
//        for (int i = 0; i < t63.size() ; i++) {
//            System.out.println(t63.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 64 input:"1/4 Billion U.S. dollars" output:"1/4 Billion U.S. dollars"
//        Document doc64 = new Document("1","12-05","1","1/4 billion U.S. dollars");
//        Parse parse64 = new Parse();
//        List<Term> t64 = parse64.parseText(doc64);
//        for (int i = 0; i <   t64.size() ; i++) {
//            System.out.println(t64.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//
//
//        // test Num 65 input:"$459,999,999" output:"459.999 M Dollars"
//        Document doc65 = new Document("1","12-05","1","$459,999,999");
//        Parse parse65 = new Parse();
//        List<Term> t65 = parse65.parseText(doc65);
//        for (int i = 0; i < t65.size() ; i++) {
//            System.out.println(t65.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 66 input:"$450,000.90001 million" output:"450000.9 Dollars million"
//        Document doc66 = new Document("1","12-05","1","$450,000.90001 million");
//        Parse parse66 = new Parse();
//        List<Term> t66 = parse66.parseText(doc66);
//        for (int i = 0; i < t66.size() ; i++) {
//            System.out.println(t66.get(i).toString());
//            System.out.println("\n");
//        }
//
//        // test Num 67 input:"$459,999,999.9" output:"459.999 M Dollars"
//        Document doc67 = new Document("1","12-05","1","$459,999,999.9");
//        Parse parse67 = new Parse();
//        List<Term> t67 = parse67.parseText(doc67);
//        for (int i = 0; i < t67.size() ; i++) {
//            System.out.println(t67.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        // test Num 68 input:"$4,450,000.90001 BiLlion" output:"4.45 M Dollars BiLlion"
//        Document doc68 = new Document("1","12-05","1","$4,450,000.90001 BiLlion");
//        Parse parse68 = new Parse();
//        List<Term> t68 = parse68.parseText(doc68);
//        for (int i = 0; i < t68.size() ; i++) {
//            System.out.println(t68.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//        //test Num 2
//        Document doc2 = new Document("2","12-05","1","123 Thousand");
//        Parse parse2 = new Parse();
//        List<Term> t2 = parse2.parseText(doc2);
//        for (int i = 0; i < t2.size() ; i++) {
//            System.out.println(t2.get(i).toString());
//            System.out.println("\n");
//        }
//
//
//
//
//        //test Num 1
//        Document doc = new Document("1","12-05","1","10,123");
//        Parse parse = new Parse();
//        List<Term> t = parse.parseText(doc);
//        for (int i = 0; i < t.size() ; i++) {
//            System.out.println(t.get(i).toString());
//            System.out.println("\n");
//        }
//


    @org.junit.Test
    public void punctuationRemove() {
    }

    @org.junit.Test
    public void inputCheckForIndex() {
    }

    @org.junit.Test
    public void isNumberMainFunction() {
    }

    @org.junit.Test
    public void isWordMainFunction() {
        // test Num 200 input:"tair" output:"tair"


        //test Num 200 input:"tair" output:"tair"
//        Document doc200 = new Document("1","12-05","1","tair");
//        List<Term> t200 = parse200.parseText(doc200);
//        for (int i = 0; i < t200.size() ; i++) {
//            System.out.println(t200.get(i).toString());
//            System.out.println("\n");
//        }

        //test Num 201 input:"tair cohen" output:"tair" , "cohen"
//        Document doc200 = new Document("1","12-05","1","tair cohen");
//        List<Term> t200 = parse200.parseText(doc200);
//        for (int i = 0; i < t200.size() ; i++) {
//            System.out.println(t200.get(i).toString());
//            System.out.println("\n");
//        }

        //test Num 202 input:"June 4" output:"06-04"
//        Document doc200 = new Document("1","12-05","1","June 4");
//        List<Term> t200 = parse200.parseText(doc200);
//        for (int i = 0; i < t200.size() ; i++) {
//            System.out.println(t200.get(i).toString());
//            System.out.println("\n");
        //}

        //test Num 203 input:"JAn 14" output:"01-14"
//        Document doc200 = new Document("1","12-05","1","JAn 14");
//        List<Term> t200 = parse200.parseText(doc200);
//        for (int i = 0; i < t200.size() ; i++) {
//            System.out.println(t200.get(i).toString());
//            System.out.println("\n");
//        }

        //test Num 204 input:"May 1994" output:"1994-05"
//        Document doc200 = new Document("1","12-05","1","May 1994");
//        List<Term> t200 = parse200.parseText(doc200);
//        for (int i = 0; i < t200.size() ; i++) {
//            System.out.println(t200.get(i).toString());
//            System.out.println("\n");
//        }

        //test Num 205 input:"hey-you" output:"hey-you" ,"hey", "you"
//        Document doc200 = new Document("1","12-05","1","hey-you");
//        List<Term> t200 = parse200.parseText(doc200);
//        for (int i = 0; i < t200.size() ; i++) {
//            System.out.println(t200.get(i).toString());
//            System.out.println("\n");
//        }

        //test Num 206 input:"5-Tair" output:"5-Tair", "Tair", "5"
//        Document doc200 = new Document("1","12-05","1","5-Tair");
//        List<Term> t200 = parse200.parseText(doc200);
//        for (int i = 0; i < t200.size() ; i++) {
//            System.out.println(t200.get(i).toString());
//            System.out.println("\n");
//        }

        //test Num 207 input:"Tair-5" output:"Tair-5","Tair", "5"
//        Document doc200 = new Document("1","12-05","1","Tair-5");
//        List<Term> t200 = parse200.parseText(doc200);
//        for (int i = 0; i < t200.size() ; i++) {
//            System.out.println(t200.get(i).toString());
//            System.out.println("\n");
//        }

        //test Num 208 input:"4-5" output:"4-5","4", "5"
//        Document doc200 = new Document("1","12-05","1","4-5");
//        List<Term> t200 = parse200.parseText(doc200);
//        for (int i = 0; i < t200.size() ; i++) {
//            System.out.println(t200.get(i).toString());
//            System.out.println("\n");
//        }

        //test Num 209 input:"4-5-6" output:"4-5-6","4", "5","6"
//        Document doc200 = new Document("1","12-05","1","4-5-6");
//        List<Term> t200 = parse200.parseText(doc200);
//        for (int i = 0; i < t200.size() ; i++) {
//            System.out.println(t200.get(i).toString());
//            System.out.println("\n");
//        }

        //test Num 210 input:"Between 18 and 34" output: "Between 18 and 34", "Between", "18" , "34"
//        Document doc200 = new Document("1","12-05","1","Between 18 and 34");
//        List<Term> t200 = parse200.parseText(doc200);
//        for (int i = 0; i < t200.size() ; i++) {
//            System.out.println(t200.get(i).toString());
//            System.out.println("\n");
//        }

        //test Num 211 input:"Tair Cohen The Third" output: "Tair Cohen The Third"
//        Document doc200 = new Document("1","12-05","1","Tair Cohen The Third");
//        List<Term> t200 = parse200.parseText(doc200);
//        for (int i = 0; i < t200.size() ; i++) {
//            System.out.println(t200.get(i).toString());
//            System.out.println("\n");
        // }
    }

    @org.junit.Test
    public void isFraction() {
    }

    @org.junit.Test
    public void saveTerms() {
    }


}