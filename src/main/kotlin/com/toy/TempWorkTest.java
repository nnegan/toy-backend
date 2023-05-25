package com.toy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TempWorkTest {

    public static void main(String[] args){
       /* Day day = Day.from("mon");
        System.out.println(day.getDay());*/

        SmartPhone.getPhone(500001);

        List<Integer> list = List.of(1, 2, 3);

    }

}

class Galaxy implements SmartPhone {}
class IPhone implements SmartPhone {}
interface SmartPhone {

    public static SmartPhone getPhone(int price) {
        if(price > 100000) {
            return new IPhone();
        }

        if(price > 50000) {
            return new Galaxy();
        }

        return null;

    }
}
