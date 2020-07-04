class GhCd99d2a85ba5febfd98d645f95b2b10a45da12216_2{
private static void validateFileName(String name) {
        // file names can have no slashes/ can't be blan or
        // can't start with a period (for it'll mean its hidden)
        // can't contain " to protect against xss
        if (StringUtils.isBlank(name) || name.contains("/") || name.startsWith(".") ||
                name.contains("\"") || name.contains("&")) {
            ValidatorException.raiseException("cobbler.snippet.invalidfilename.message");
        }
    }
}