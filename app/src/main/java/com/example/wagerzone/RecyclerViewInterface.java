
package com.example.wagerzone;
/**
 *
 * @author Maxime Malette
 * @version 1.0
 *
 * Cette interface permet d'implémenter des recyclerView dans nos activités.
 */
public interface RecyclerViewInterface {
    /**
     *
     * @param position La position de l'item dans le recycler view.
     */
    void onItemClick(int position);
}
