package cupcakes.br.cbi.commons

import cupcakes.br.cbi.models.Client

/**
 * Created by monitorapc on 20-Nov-17.
 */
interface ClientInterface {

    fun onClientSearch(list_clients_searched: ArrayList<Client>)

}