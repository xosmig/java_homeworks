
def generate(lower):
    upper = lower.title()
    return "" +\
        "@Test\n" +\
        "public void serialize" + upper + "Test() throws Exception {\n" +\
        "    final int n = 10;\n" +\
        "    Random random = new Random();\n" +\
        "    " + lower + "[] arr = new " + lower + "[n];\n" +\
        "    ByteArrayOutputStream os = new ByteArrayOutputStream();\n" +\
        "\n" +\
        "    for(int i = 0; i < n; i++) {\n" +\
        "       arr[i] = random.next" + upper + "();\n" +\
        "       Serialization.serialize" + upper + "(arr[i], os);\n" +\
        "    }\n" +\
        "\n" +\
        "    InputStream is = new ByteArrayInputStream(os.toByteArray());\n" +\
        "    for(int i = 0; i < n; i++) {\n" +\
        "       assertEquals(arr[i], Serialization.deserialize" + upper + "(is));\n" +\
        "    }\n" +\
        "}\n" +\
        "\n"


def main():
    # even this is tedious
    print(generate("boolean"))
    print(generate("byte"))
    print(generate("char"))
    print(generate("short"))
    print(generate("int"))
    print(generate("long"))
    print(generate("float"))
    print(generate("double"))


main()
