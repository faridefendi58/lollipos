package id.web.jagungbakar.lollipos.techicalservices.params;

import java.util.List;

import id.web.jagungbakar.lollipos.domain.params.Params;

/**
 *
 * @author Farid Efendi
 *
 */
public interface ParamDao {

    /**
     * Add customer data
     * @param param
     * @return
     */
    int addParam(Params param);

    /**
     * Edit customer
     * @param param
     * @return
     */
    boolean editParam(Params param);

    /**
     * Returns param finds by id.
     * @param id
     * @return
     */
    Params getParamById(int id);

    Params getParamByName(String name);

    /**
     * Returns list of all param
     * @return list of all param
     */
    List<Params> getAllParams();

    /**
     * @param search
     * @return
     */
    List<Params> searchParam(String search);

    void clearParamCatalog();

    void suspendParam(Params param);
}


