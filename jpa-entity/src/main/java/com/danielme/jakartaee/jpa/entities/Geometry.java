package com.danielme.jakartaee.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Entity
@Table(name = "geometries")
@Getter
public class Geometry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum Shape {
        CIRCLE(1),
        RECTANGLE(2),
        TRIANGLE(3);

        private final Integer id;
        private static final Map<Integer, Shape> reverse;

        static {
            reverse = new HashMap<>();
            for (Shape shape : Shape.values()) {
                reverse.put(shape.id(), shape);
            }
        }

        Shape(int id) {
            this.id = id;
        }

        public Integer id() {
            return id;
        }

        public static Shape byId(Integer id) {
            Shape result = reverse.get(id);
            if (result == null) {
                throw new NoSuchElementException("shape with id " + id + " does not exist");
            }
            return result;
        }
    }

    @Column(columnDefinition = "TINYINT")
    private Shape shape;

}
