package logic;

import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class PDU {

    private static HashMap<Character, Byte> alphabet = new HashMap<Character, Byte>();

    private String encodedSMS;

    private String PDUlength;

    private String destination;

    private String firstOctet;

    private String senderId;

    private String protocolIdentifier;

    private String dataCodingScheme;

    private String timeStamp;

    private String userDataLength;

    private String userDataMessageString;




    public PDU(String message, int sender, int destination) {
        setAlphabet();
        this.destination = transform(destination);
        firstOctet = transform(4);
        senderId = transform(sender);
        protocolIdentifier = transform(0);
        dataCodingScheme = transform(0);
        timeStamp = getTimeStamp();
        userDataMessageString = convert(message);
        userDataLength = transform(userDataMessageString.length() / 2);
        PDUlength = transform((firstOctet.length() + senderId.length() + protocolIdentifier.length() + dataCodingScheme.length()
                                + timeStamp.length() + userDataMessageString.length()) / 2 + userDataLength.length());
        encodedSMS = PDUlength + this.destination + firstOctet + senderId + protocolIdentifier + dataCodingScheme + timeStamp + userDataLength + userDataMessageString;
    }

    public String getEncodedSMS() {
        return encodedSMS;
    }

    private static String getTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        Date currentDate = new Date();

        ZoneOffset offset = ZoneOffset.systemDefault().getRules().getOffset(java.time.Instant.now());

        String deviation = offset.toString();
        String tpScts = dateFormat.format(currentDate);

        return  tpScts + convertIntoTimeStamp(deviation);
    }

    private static String convertIntoTimeStamp(String timeZone) {
        int result = 0;
        if(timeZone.charAt(0) == '+'){
            result = 1;
        }
        result = result << 7;
        result += Integer.parseInt(timeZone.substring(1, 3));
        return Integer.toHexString(result);
    }
    public static String convert(String message) {
        StringBuilder converted = new StringBuilder();
        for(int i = 0; i < message.length(); i++) {
            char temp = message.charAt(i);
            converted.append(transform(alphabet.get(temp)));
        }
        return converted.toString();
    }
    private static String transform(int num) {
        if(num < 16){
            return "0" + Integer.toHexString(num);
        } else return Integer.toHexString(num);
    }
    public static void setAlphabet() {
        alphabet.put('@', (byte) 0);
        alphabet.put('£', (byte) 1);
        alphabet.put('$', (byte) 2);
        alphabet.put('¥', (byte) 3);
        alphabet.put('è', (byte) 4);
        alphabet.put('é', (byte) 5);
        alphabet.put('ù', (byte) 6);
        alphabet.put('ì', (byte) 7);
        alphabet.put('ò', (byte) 8);
        alphabet.put('Ç', (byte) 9);
        alphabet.put('\n', (byte) 10);
        alphabet.put('Ø', (byte) 11);
        alphabet.put('ø', (byte) 12);
        alphabet.put('\r', (byte) 13);
        alphabet.put('Å', (byte) 14);
        alphabet.put('å', (byte) 15);
        alphabet.put('_', (byte) 17);
        alphabet.put('Ã', (byte) 19);
        alphabet.put('Ë', (byte) 20);
        alphabet.put('Ù', (byte) 21);
        alphabet.put('Ð', (byte) 22);
        alphabet.put('Ó', (byte) 24);
        alphabet.put('È', (byte) 25);
        alphabet.put('Î', (byte) 26);
        alphabet.put('Æ', (byte) 28);
        alphabet.put('æ', (byte) 29);
        alphabet.put('ß', (byte) 30);
        alphabet.put('É', (byte) 31);
        alphabet.put(' ', (byte) 32);
        alphabet.put('!', (byte) 33);
        alphabet.put('"', (byte) 34);
        alphabet.put('#', (byte) 35);
        alphabet.put('¤', (byte) 36);
        alphabet.put('%', (byte) 37);
        alphabet.put('&', (byte) 38);
        alphabet.put('\'', (byte) 39);
        alphabet.put('(', (byte) 40);
        alphabet.put(')', (byte) 41);
        alphabet.put('*', (byte) 42);
        alphabet.put('+', (byte) 43);
        alphabet.put(',', (byte) 44);
        alphabet.put('-', (byte) 45);
        alphabet.put('.', (byte) 46);
        alphabet.put('/', (byte) 47);
        alphabet.put('0', (byte) 48);
        alphabet.put('1', (byte) 49);
        alphabet.put('2', (byte) 50);
        alphabet.put('3', (byte) 51);
        alphabet.put('4', (byte) 52);
        alphabet.put('5', (byte) 53);
        alphabet.put('6', (byte) 54);
        alphabet.put('7', (byte) 55);
        alphabet.put('8', (byte) 56);
        alphabet.put('9', (byte) 57);
        alphabet.put(':', (byte) 58);
        alphabet.put(';', (byte) 59);
        alphabet.put('<', (byte) 60);
        alphabet.put('=', (byte) 61);
        alphabet.put('>', (byte) 62);
        alphabet.put('?', (byte) 63);
        alphabet.put('¡', (byte) 64);
        alphabet.put('A', (byte) 65);
        alphabet.put('B', (byte) 66);
        alphabet.put('C', (byte) 67);
        alphabet.put('D', (byte) 68);
        alphabet.put('E', (byte) 69);
        alphabet.put('F', (byte) 70);
        alphabet.put('G', (byte) 71);
        alphabet.put('H', (byte) 72);
        alphabet.put('I', (byte) 73);
        alphabet.put('J', (byte) 74);
        alphabet.put('K', (byte) 75);
        alphabet.put('L', (byte) 76);
        alphabet.put('M', (byte) 77);
        alphabet.put('N', (byte) 78);
        alphabet.put('O', (byte) 79);
        alphabet.put('P', (byte) 80);
        alphabet.put('Q', (byte) 81);
        alphabet.put('R', (byte) 82);
        alphabet.put('S', (byte) 83);
        alphabet.put('T', (byte) 84);
        alphabet.put('U', (byte) 85);
        alphabet.put('V', (byte) 86);
        alphabet.put('W', (byte) 87);
        alphabet.put('X', (byte) 88);
        alphabet.put('Y', (byte) 89);
        alphabet.put('Z', (byte) 90);
        alphabet.put('Ä', (byte) 91);
        alphabet.put('Ö', (byte) 92);
        alphabet.put('Ñ', (byte) 93);
        alphabet.put('Ü', (byte) 94);
        alphabet.put('§', (byte) 95);
        alphabet.put('¿', (byte) 96);
        alphabet.put('a', (byte) 97);
        alphabet.put('b', (byte) 98);
        alphabet.put('c', (byte) 99);
        alphabet.put('d', (byte) 100);
        alphabet.put('e', (byte) 101);
        alphabet.put('f', (byte) 102);
        alphabet.put('g', (byte) 103);
        alphabet.put('h', (byte) 104);
        alphabet.put('i', (byte) 105);
        alphabet.put('j', (byte) 106);
        alphabet.put('k', (byte) 107);
        alphabet.put('l', (byte) 108);
        alphabet.put('m', (byte) 109);
        alphabet.put('n', (byte) 110);
        alphabet.put('o', (byte) 111);
        alphabet.put('p', (byte) 112);
        alphabet.put('q', (byte) 113);
        alphabet.put('r', (byte) 114);
        alphabet.put('s', (byte) 115);
        alphabet.put('t', (byte) 116);
        alphabet.put('u', (byte) 117);
        alphabet.put('v', (byte) 118);
        alphabet.put('w', (byte) 119);
        alphabet.put('x', (byte) 120);
        alphabet.put('y', (byte) 121);
        alphabet.put('z', (byte) 122);
        alphabet.put('ä', (byte) 123);
        alphabet.put('ö', (byte) 124);
        alphabet.put('ñ', (byte) 125);
        alphabet.put('ü', (byte) 126);
        alphabet.put('à', (byte) 127);
    }

}
