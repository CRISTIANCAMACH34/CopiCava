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
package uniandes.cupi2.cupiCava.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Panel con la lista de vinos de la cava.
 */
public class PanelListaVinos extends JPanel implements ListSelectionListener, ActionListener
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante que representa el comando de agregar un vino.
     */
    private final static String AGREGAR = "Agregar";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Ventana principal de la aplicación.
     */
    private InterfazCupiCava principal;

    // -----------------------------------------------------------------
    // Atributos de la Interfaz
    // -----------------------------------------------------------------

    /**
     * Lista de los vinos.
     */
    // TODO Parte3 PuntoA: Declare el atributo listaVinos de tipo JList.
    private JList listaVinos; // Declaración del atributo JList

    /**
     * Panel con un scroll que contiene a listaVinos.
     */
    private JScrollPane scroll;

    /**
     * Botón para agregar un nuevo vino.
     */
    private JButton botonAgregar;

    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------

    /**
     * Constructor del panel.
     * @param pPrincipal Ventana principal de la aplicación. pPrincipal != null.
     */
    public PanelListaVinos( InterfazCupiCava pPrincipal )
    {
        principal = pPrincipal;

        setLayout( new BorderLayout( ) );
        setBorder( new CompoundBorder( new EmptyBorder( 0, 5, 0, 5 ), new TitledBorder( "Lista de vinos" ) ) );
        setPreferredSize( new Dimension( 250, 0 ) );

        // TODO Parte3 PuntoB: Inicializar la lista de vinos y agregarle un ListSelectionListener
        listaVinos = new JList( ); // Inicializa JList
        listaVinos.setSelectionMode( ListSelectionModel.SINGLE_SELECTION ); // Permite seleccionar un solo elemento
        listaVinos.addListSelectionListener( this ); // Agrega el listener para capturar selecciones


        // TODO Parte3 PuntoC: Inicializar el scroll.
        // Aquí se inicializa el JScrollPane, envolviendo la listaVinos
        scroll = new JScrollPane( listaVinos ); // <--- SOLUCIÓN: Inicializar 'scroll' aquí
        scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        scroll.setBorder( new CompoundBorder( new EmptyBorder( 3, 3, 3, 3 ), new LineBorder( Color.BLACK, 1 ) ) );

        botonAgregar = new JButton( AGREGAR );
        botonAgregar.setActionCommand( AGREGAR );
        botonAgregar.addActionListener( this );

        add( scroll, BorderLayout.CENTER );
        add( botonAgregar, BorderLayout.SOUTH );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Actualiza la lista de vinos con la lista recibida por parámetro.
     * @param pListaVinos Lista de los vinos. pListaVinos != null.
     */
    public void refrescarLista( ArrayList pListaVinos )
    {
        listaVinos.setListData( pListaVinos.toArray( ) );
        if( !pListaVinos.isEmpty( ) )
        {
            listaVinos.setSelectedIndex( 0 );
        }
    }

    /**
     * Actualiza el vino seleccionado.
     * @param pNombreVino Nombre del vino seleccionado. pNombreVino != null && pNombreVino != "".
     */
    public void seleccionar( String pNombreVino )
    {
        int indice = -1;
        ListModel model = listaVinos.getModel( );
        for( int i = 0; i < model.getSize( ); i++ )
        {
            String vinoActual = ( String )model.getElementAt( i );
            if( vinoActual.equals( pNombreVino ) )
            {
                indice = i;
            }
        }

        listaVinos.setSelectedIndex( indice );
        listaVinos.ensureIndexIsVisible( indice );
    }

    /**
     * Atiende el evento cuando el usuario selecciona un vino de la lista.
     * @param pEvento Evento de selección de un elemento de la lista de vinos. pEvento != null.
     */
    public void valueChanged( ListSelectionEvent pEvento )
    {
        if( listaVinos.getSelectedValue( ) != null )
        {
            // Se asume que los elementos de la lista son de tipo String o que su toString() retorna el nombre
            // Si los elementos de la lista son objetos Vino, se debería castear a Vino y luego darNombre().
            // Por el contexto del método refrescarLista (usa pListaVinos.toArray()), es probable que sean Strings o que Vino.toString() sea el nombre.
            String nombreVino = ( String )listaVinos.getSelectedValue( );
            principal.actualizarInfoVino( nombreVino );
        }
    }

    /**
     * Manejo de los eventos de los botones.
     * @param pEvento Acción que generó el evento.
     */
    public void actionPerformed( ActionEvent pEvento )
    {
        String comando = pEvento.getActionCommand( );
        if( comando.equals( AGREGAR ) )
        {
            DialogoAgregarVino dialogoAgregar = new DialogoAgregarVino( principal );
            dialogoAgregar.setVisible( true );
        }
    }

}