package com.example;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SINGLE-FILE EXERCISE: sealed, non-sealed, and final.
 * Requires Java 17+. No dependencies or preview features.
 *
 * Compile: javac --release 17 SealedExercise.java
 * Run:     java SealedExercise
 * Or:      java SealedExercise.java
 *
 * STUDENT TASKS
 * 1. Predict each type's sealed/final status before running.
 * 2. Uncomment ONE invalid example at a time; explain the diagnostic.
 * 3. Add a final Ellipse implementing Shape. Update the permits clause.
 * 4. Add a final Triangle extending Polygon. Update Polygon's permits.
 * 5. Add another subclass of CustomShape without changing Shape's permits.
 * 6. With Java 21+, enable describe21 below. Remove a branch and compile.
 *    Then replace `case CustomShape` with `case Star`: why is it incomplete?
 * 7. Explain why sealing does not guarantee immutability.
 *
 * RULES
 * - permits lists DIRECT subtypes, not every descendant.
 * - A direct ordinary class subtype must be final, sealed, or non-sealed.
 * - A direct interface subtype must be sealed or non-sealed (never final).
 * - Records are implicitly final; enums are implicitly final or sealed.
 * - non-sealed requires a direct sealed superclass or superinterface.
 * - Sealing controls inheritance; it does not control method overriding.
 * - Permitted subtypes must be in the same named module; when there is no
 *   named module, they must be in the same package. They must be accessible.
 * - Sealed types need at least one permitted subtype. Local/anonymous types
 *   cannot be permitted direct subtypes. No wildcards in a permits clause.
 * - sealed/non-sealed apply to classes/interfaces, not methods or variables.
 */
public final class SealedExercise01 {
    private SealedExercise01() { }

    // 1. SEALED INTERFACE: an explicitly restricted set of direct subtypes.
    sealed interface Shape permits Circle, Polygon, CustomShape, Point {
        double area();
    }

    // 2. FINAL CLASS: this branch ends here. Fields are final separately.
    static final class Circle implements Shape {
        private final double radius;

        Circle(double radius) {
            this.radius = positive(radius);
        }

        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
    }

    // 3. SEALED CLASS: restrict inheritance again inside the sealed hierarchy.
    static abstract sealed class Polygon implements Shape permits Rectangle {
        private final String name; // Blank final: initialized in constructor.

        Polygon(String name) {
            this.name = name;
        }

        // A final method cannot be overridden, even by a permitted subclass.
        public final String name() {
            return name;
        }
    }

    static final class Rectangle extends Polygon {
        private final double width;
        private final double height;

        Rectangle(double width, double height) {
            super("Rectangle");
            this.width = positive(width);
            this.height = positive(height);
        }

        @Override
        public double area() {
            return width * height;
        }
    }

    // 4. NON-SEALED CLASS: reopen this branch to arbitrary descendants.
    static non-sealed class CustomShape implements Shape {
        private double area; // Mutable: sealed hierarchies can contain state.

        CustomShape(double area) {
            this.area = positive(area);
        }

        public void resize(double area) {
            this.area = positive(area);
        }

        @Override
        public double area() {
            return area;
        }
    }

    // Ordinary subclass is legal: its DIRECT parent is non-sealed.
    static class Star extends CustomShape {
        Star(double area) {
            super(area);
        }
    }

    // A descendant may choose to close its own branch.
    static final class ColoredStar extends Star {
        ColoredStar(double area) {
            super(area);
        }
    }

    // 5. RECORD: implicitly final; can implement a sealed interface.
    // A record cannot extend Polygon: its superclass is java.lang.Record.
    record Point(double x, double y) implements Shape {
        @Override
        public double area() {
            return 0.0;
        }
    }

    // 6. INFERRED PERMITS: direct subtypes are in this compilation unit.
    sealed interface Event { }

    record Started(String job) implements Event { }

    // NON-SEALED INTERFACE: arbitrary classes/interfaces may implement/extend.
    non-sealed interface ExtensionEvent extends Event { }

    static class PluginEvent implements ExtensionEvent { }

    // An enum with NO constant-specific class bodies is implicitly final.
    enum Finished implements Event { SUCCESS, FAILURE }

    // An enum WITH a constant-specific class body is implicitly sealed.
    // Its permitted subclasses are its compiler-created constant subclasses.
    enum Action implements Event {
        START {
            @Override
            String message() { return "Starting"; }
        };

        abstract String message();
    }

    // 7. RUNTIME REFLECTION: permitted subtypes are direct, not transitive.
    static void inspect(Class<?> type) {
        Class<?>[] permitted = type.getPermittedSubclasses();
        String children = permitted == null ? "[]" : Arrays.toString(
                Arrays.stream(permitted).map(child -> child.getSimpleName().isEmpty()
                        ? child.getName() : child.getSimpleName()).sorted().toArray());
        System.out.printf("%-16s sealed=%-5s final=%-5s permits=%s%n",
                type.getSimpleName(), type.isSealed(),
                Modifier.isFinal(type.getModifiers()), children);
        // For a non-sealed type, getPermittedSubclasses() returns null.
        // Reflection has no isNonSealed(): an ordinary open class also reports
        // false for isSealed() and false for the final modifier.
    }

    // 8. FINAL VARIABLE/PARAMETER: prevents reassignment, not object mutation.
    static void demonstrateFinal(final CustomShape shape) {
        final List<String> notes = new ArrayList<>();
        notes.add("Mutation through a final reference is legal.");
        shape.resize(25.0);
        System.out.println(notes.get(0));
        System.out.println("Area after mutation: " + shape.area());
        // notes = new ArrayList<>(); // ERROR: final local reassignment.
        // shape = new CustomShape(1); // ERROR: final parameter reassignment.
    }

    // Valid Java 17 instanceof pattern matching (no preview needed).
    static String describe(Shape shape) {
        if (shape instanceof Circle) return "Final circle";
        if (shape instanceof Polygon p) return "Sealed polygon: " + p.name();
        if (shape instanceof CustomShape) return "Open custom-shape branch";
        if (shape instanceof Point p) return "Record point: " + p;
        throw new IllegalArgumentException("Shape is null or unrecognized");
        // Unlike a pattern switch, this if-chain has no exhaustiveness check.
    }

    /* 9. OPTIONAL JAVA 21+ EXERCISE: uncomment this complete method.
    No default is needed because all permitted branches are covered.
    CustomShape's pattern also covers its UNKNOWN future descendants.
    `case null` handles null explicitly; without it, null causes an NPE.
    Put a specific Star case BEFORE CustomShape; otherwise it is dominated.
 
     */
 static String describe21(Shape shape) {
     return switch (shape) {
         case null          -> "No shape";
         case Circle c      -> "Circle area=" + c.area();
         case Polygon p     -> p.name() + " area=" + p.area();
         case CustomShape c -> "Extension area=" + c.area();
         case Point p       -> "Point(" + p.x() + ", " + p.y() + ")";
     };
 }

 // 10. COMPILER EXPERIMENTS: uncomment ONE example at a time.
 // static final class Rogue implements Shape {
 //     public double area() { return 1; }
 // } // ERROR: Rogue is not in Shape's permits list.

 // static class SpecialCircle extends Circle {
 //     SpecialCircle() { super(1); }
 // } // ERROR: Circle is final.

 // static non-sealed class Unrelated { }
 // ERROR: no direct sealed superclass or superinterface.

 // final interface Impossible { }
 // ERROR: interfaces cannot be final.

 // static final sealed class Conflicting { }
 // ERROR: final/sealed/non-sealed are mutually exclusive.

 // static class OtherRectangle extends Polygon {
 //     OtherRectangle() { super("Other"); }
 //     public double area() { return 1; }
 // } // ERRORS: not permitted; missing final/sealed/non-sealed.
 // To isolate the missing-modifier error, first add it to Polygon's permits.

 // In Rectangle, add:
 // public String name() { return "Changed"; }
 // ERROR: cannot override Polygon's final method.

 // In main, add:
 // Shape anonymous = new Shape() { public double area() { return 1; } };
 // ERROR: anonymous direct implementation of a sealed interface.
 // Anonymous implementations of ExtensionEvent ARE legal.

 // static final class UnrelatedFinal { }
 // static UnrelatedFinal impossibleCast(Shape s) {
 //     return (UnrelatedFinal) s;
 // } // ERROR: disjoint types; UnrelatedFinal cannot ever be a Shape.

 // Also try: remove Rectangle's final modifier; list Rectangle in Shape's
 // permits (it is indirect); or add String to Shape's permits (not a subtype).

 static double positive(double value) {
     if (!Double.isFinite(value) || value <= 0) {
         throw new IllegalArgumentException("Expected a positive finite value");
     }
     return value;
 }

 public static void main(String[] args) {
     List<Shape> shapes = List.of(new Circle(2), new Rectangle(3, 4),
             new CustomShape(5), new Star(10), new ColoredStar(15), new Point(1, 2));
     for (Shape shape : shapes) {
         System.out.printf("%-32s area=%.2f%n", describe(shape), shape.area());
     }

     System.out.println("\nInheritance policies:");
     for (Class<?> type : List.of(Shape.class, Circle.class, Polygon.class,
             Rectangle.class, CustomShape.class, Star.class, Point.class,
             Event.class, ExtensionEvent.class, Finished.class, Action.class)) {
         inspect(type);
     }

     System.out.println("\nFinal references and mutable objects:");
     demonstrateFinal(new CustomShape(5));
     System.out.println("\nAll demonstrations completed.");
 }
}
