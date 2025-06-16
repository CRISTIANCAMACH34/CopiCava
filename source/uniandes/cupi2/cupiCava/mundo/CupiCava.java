/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n7_cupiCava
 * Autor: Equipo Cupi2 2020
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package uniandes.cupi2.cupiCava.mundo;

import java.util.ArrayList; // Necesario para ArrayList
import java.util.Iterator;  // Útil para algunos recorridos, aunque no siempre estrictamente necesario con for-each

/**
 * Clase que representa la cava de vinos.
 */
public class CupiCava {
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Lista de vinos de la cava.
     */
    private ArrayList<Vino> vinos;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye una nueva cava vacía.
     */
    public CupiCava() {
        vinos = new ArrayList<>();
        verificarInvariante();
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Agrega un nuevo vino a la cava.
     * Si ya existe un vino con el mismo nombre, no lo agrega.
     * @param pNombre Nombre del vino. pNombre != null && pNombre != "".
     * @param pPresentacion Presentación del vino. pPresentacion != null && pPresentacion != "" && (pPresentacion == BOTELLA || pPresentacion == BARRIL).
     * @param pAnhoElaboracion Año de elaboración del vino. pAnhoElaboracion > 0.
     * @param pContenidoAzucar Contenido en azúcar del vino. pContenidoAzucar >= 0
     * @param pTipo Tipo de vino de acuerdo a su contenido en azúcar. pTipo != null && pTipo != "" && (pTipo == SECO || pTipo == ABOCADO || pTipo == SEMI_SECO || pTipo ==
     * SEMI_DULCE || pTipo == DULCE).
     * @param pColor Color del vino. pColor != null && pColor != "" && (pColor == TINTO || pColor == ROSADO || pColor == BLANCO).
     * @param pLugarOrigen Lugar de origen del vino. lugarElaboracion != null y lugarElaboracion != "".
     * @param pImagen Imagen del vino. pImagen != null && pImagen != "".
     * @return true si el vino fue agregado, false en caso contrario (ya existía un vino con ese nombre).
     */
    public boolean agregarVino(String pNombre, String pPresentacion, int pAnhoElaboracion, double pContenidoAzucar,
                               String pTipo, String pColor, String pLugarOrigen, String pImagen) {
        if (buscarVino(pNombre) == null) {
            Vino nuevoVino = new Vino(pNombre, pPresentacion, pAnhoElaboracion, pContenidoAzucar,
                                      pTipo, pColor, pLugarOrigen, pImagen);
            vinos.add(nuevoVino);
            verificarInvariante();
            return true;
        }
        return false;
    }

    /**
     * Retorna la lista de vinos de la cava.
     * @return Lista de vinos.
     */
    public ArrayList<Vino> darVinos() {
        return vinos;
    }

    /**
     * Busca el primer vino con el nombre dado utilizando búsqueda secuencial.
     * Este método es útil para verificar duplicados o para la selección inicial.
     * @param pNombre Nombre del vino a buscar.
     * @return El vino encontrado, o null si no existe.
     */
    public Vino buscarVino(String pNombre) {
        for (Vino vino : vinos) {
            if (vino.darNombre().equals(pNombre)) {
                return vino;
            }
        }
        return null;
    }

    // -----------------------------------------------------------------
    // Métodos de Ordenamiento
    // -----------------------------------------------------------------

    /**
     * Ordena la lista de vinos por nombre (ascendente).
     * Implementado con el algoritmo de burbuja.
     */
    public void ordenarVinosPorNombre() {
        for (int i = 0; i < vinos.size() - 1; i++) {
            for (int j = 0; j < vinos.size() - 1 - i; j++) {
                if (vinos.get(j).compararPorNombre(vinos.get(j + 1)) > 0) {
                    Vino temp = vinos.get(j);
                    vinos.set(j, vinos.get(j + 1));
                    vinos.set(j + 1, temp);
                }
            }
        }
        verificarInvariante();
    }

    /**
     * Ordena la lista de vinos por año de elaboración (descendente).
     * Implementado con el algoritmo de selección.
     */
    public void ordenarVinosPorAnhoElaboracion() {
        for (int i = 0; i < vinos.size() - 1; i++) {
            int maxIndex = i; // Cambiado a maxIndex para descendente
            for (int j = i + 1; j < vinos.size(); j++) {
                // Si el año actual es MAYOR que el año en maxIndex, actualiza maxIndex
                if (vinos.get(j).compararPorAnhoElaboracion(vinos.get(maxIndex)) < 0) { // <0 porque compararPorAnhoElaboracion devuelve -1 si actual > otro
                    maxIndex = j;
                }
            }
            // Intercambiar el elemento actual con el encontrado
            Vino temp = vinos.get(i);
            vinos.set(i, vinos.get(maxIndex));
            vinos.set(maxIndex, temp);
        }
        verificarInvariante();
    }

    /**
     * Ordena la lista de vinos por lugar de origen (ascendente).
     * Implementado con el algoritmo de inserción.
     */
    public void ordenarVinosPorLugarOrigen() {
        for (int i = 1; i < vinos.size(); i++) {
            Vino actual = vinos.get(i);
            int j = i - 1;
            while (j >= 0 && vinos.get(j).compararPorLugarOrigen(actual) > 0) {
                vinos.set(j + 1, vinos.get(j));
                j--;
            }
            vinos.set(j + 1, actual);
        }
        verificarInvariante();
    }

    // -----------------------------------------------------------------
    // Métodos de Búsqueda
    // -----------------------------------------------------------------

    /**
     * Busca un vino por su nombre usando búsqueda binaria.
     * PRECONDICIÓN: La lista de vinos debe estar ordenada ascendentemente por nombre para que esta búsqueda funcione correctamente.
     * @param pNombre Nombre del vino a buscar. pNombre != null && pNombre != "".
     * @return El vino encontrado, o null si no se encuentra.
     */
    public Vino buscarBinarioPorNombre(String pNombre) {
        // Asumiendo que la lista ya está ordenada por nombre debido a la llamada en InterfazCupiCava
        int inicio = 0;
        int fin = vinos.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2; // Mejor cálculo del medio para evitar desbordamiento
            Vino vinoMedio = vinos.get(medio);
            // Usamos compareToIgnoreCase para una búsqueda insensible a mayúsculas/minúsculas
            int comparacion = vinoMedio.darNombre().compareToIgnoreCase(pNombre);

            if (comparacion == 0) {
                return vinoMedio; // Encontrado
            } else if (comparacion < 0) {
                inicio = medio + 1; // El nombre buscado es "mayor", buscar en la mitad superior
            } else {
                fin = medio - 1; // El nombre buscado es "menor", buscar en la mitad inferior
            }
        }
        return null; // No encontrado
    }

    /**
     * Busca todos los vinos de un tipo específico.
     * @param pTipo Tipo de vino a buscar (ej. Vino.SECO). pTipo != null && pTipo != "".
     * @return Una lista (ArrayList) con todos los vinos del tipo especificado. La lista puede estar vacía si no se encuentran.
     */
    public ArrayList<Vino> buscarVinosDeTipo(String pTipo) {
        ArrayList<Vino> vinosEncontrados = new ArrayList<>();
        for (Vino vino : vinos) {
            if (vino.darTipo().equalsIgnoreCase(pTipo)) { // Comparación insensible a mayúsculas/minúsculas
                vinosEncontrados.add(vino);
            }
        }
        return vinosEncontrados;
    }

    /**
     * Busca el vino con el contenido de azúcar más alto (el más dulce).
     * @return El vino más dulce, o null si la cava está vacía.
     */
    public Vino buscarVinoMasDulce() {
        if (vinos.isEmpty()) {
            return null;
        }

        Vino vinoMasDulce = vinos.get(0);
        for (int i = 1; i < vinos.size(); i++) {
            Vino actual = vinos.get(i);
            if (actual.darContenidoAzucar() > vinoMasDulce.darContenidoAzucar()) {
                vinoMasDulce = actual;
            }
        }
        return vinoMasDulce;
    }

    /**
     * Busca el vino con el contenido de azúcar más bajo (el más seco).
     * @return El vino más seco, o null si la cava está vacía.
     */
    public Vino buscarVinoMasSeco() {
        if (vinos.isEmpty()) {
            return null;
        }

        Vino vinoMasSeco = vinos.get(0);
        for (int i = 1; i < vinos.size(); i++) {
            Vino actual = vinos.get(i);
            if (actual.darContenidoAzucar() < vinoMasSeco.darContenidoAzucar()) {
                vinoMasSeco = actual;
            }
        }
        return vinoMasSeco;
    }

    // -----------------------------------------------------------------
    // Invariante de Clase
    // -----------------------------------------------------------------

    /**
     * Verifica el invariante de la clase.<br>
     * <b>Invariante:</b>
     * <ul>
     * <li>La lista de vinos no es nula.</li>
     * <li>No hay vinos con nombres repetidos en la lista.</li>
     * </ul>
     */
    private void verificarInvariante() {
        assert vinos != null : "La lista de vinos no puede ser nula";

        // Verifica que no haya vinos con nombres repetidos
        for (int i = 0; i < vinos.size(); i++) {
            for (int j = i + 1; j < vinos.size(); j++) {
                assert !vinos.get(i).darNombre().equals(vinos.get(j).darNombre()) : "Hay vinos con nombres repetidos.";
            }
        }
    }

    // -----------------------------------------------------------------
    // Puntos de Extensión (Implementación básica, puedes modificarlos)
    // -----------------------------------------------------------------

    /**
     * Método para la extensión 1.
     * @return Respuesta de la extensión 1.
     */
    public String metodo1() {
        return "Respuesta a la Extensión 1";
    }

    /**
     * Método para la extensión 2.
     * @return Respuesta de la extensión 2.
     */
    public String metodo2() {
        return "Respuesta a la Extensión 2";
    }
}