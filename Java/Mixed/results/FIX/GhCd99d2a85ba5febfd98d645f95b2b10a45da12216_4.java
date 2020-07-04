class GhCd99d2a85ba5febfd98d645f95b2b10a45da12216_4{
private static void validateCommonPath(File path) {
        if (!path.exists() || path.isHidden() || !path.isFile() || !isCommonPath(path) ||
                path.getAbsolutePath().contains("\"") ||
                path.getAbsolutePath().contains("&")) {
            ValidatorException.raiseException("cobbler.snippet.invalidfilename.message");
        }
    }
}