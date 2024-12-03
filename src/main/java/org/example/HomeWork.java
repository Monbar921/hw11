package org.example;


import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.stream.IntStream;

public class HomeWork {
    private final static int LEFT = 1;
    private final static int RIGHT = -1;

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу Step из файла contest6_tasks.pdf
     */
    @SneakyThrows
    public void stepDanceValue(InputStream in, OutputStream out) {
        AVLTree tree = new AVLTree();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String conditionsStr = reader.readLine();
            int corLength = Integer.parseInt(conditionsStr.substring(0, conditionsStr.indexOf(" ")));
            int alternations = Integer.parseInt(conditionsStr.substring(conditionsStr.indexOf(" ") + 1));

            IntStream.range(1, corLength + 1).forEach(n -> tree.add(n, LEFT));

            for (int i = 0; i < alternations; ++i) {
                int alternation = Integer.parseInt(reader.readLine());
                tree.inverseValue(alternation);
                int maxUniqueLength = tree.getMaxUniqueLength();
                out.write(String.valueOf(maxUniqueLength).getBytes());
                out.write("\r\n".getBytes());
            }
        }

        out.flush();
    }


}
