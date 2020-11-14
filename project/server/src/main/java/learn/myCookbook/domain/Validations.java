package learn.myCookbook.domain;

public class Validations {

    public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

//    public static Result<?> validateInputs(Object obj, Class c) {
//        obj = (c) obj;
//
//        Result<c> result = new Result<>();
//        System.out.println(obj.getClass());
//    }
}
