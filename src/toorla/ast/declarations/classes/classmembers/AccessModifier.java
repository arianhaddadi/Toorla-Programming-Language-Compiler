package toorla.ast.declarations.classes.classmembers;

public enum AccessModifier {
    ACCESS_MODIFIER_PUBLIC,
    ACCESS_MODIFIER_PRIVATE;

    @Override
    public String toString() {
        return "(" + super.toString() + ")";
    }
}
