class Stackoverflow46343791_1{
//Avoid input value in error message (XSS)
@ControllerAdvice
public class RestExceptionControllerAdvice {

    @ExceptionHandler(NumberFormatException.class)
    public ErrorResponse handleSearchParseException(NumberFormatException exception) {
       // do whathever you want
   }

}
}