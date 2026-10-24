package JMathEngine.exceptions;

/* JADX INFO: loaded from: classes3.dex */
public class VarNotFoundException extends RuntimeException {
    public String var_name;

    public VarNotFoundException(String var_name) {
        this.var_name = var_name;
    }
}
