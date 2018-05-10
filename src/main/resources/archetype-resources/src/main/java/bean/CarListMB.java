#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;

import ${package}.infra.model.Filter;
import ${package}.model.Car;
import ${package}.service.CarService;
import com.github.adminfaces.template.exception.BusinessException;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static ${package}.util.Utils.addDetailMessage;

/**
 * Created by rmpestano on 12/02/17.
 */
@Named
@ViewScoped
public class CarListMB implements Serializable {

    @Inject
    CarService carService;

    Integer id;

    LazyDataModel<Car> cars;

    Filter<Car> filter = new Filter<>(new Car());

    List<Car> selectedCars; //cars selected in checkbox column

    List<Car> filteredValue;// datatable filteredValue attribute (column filters)

    @PostConstruct
    public void initDataModel() {
        cars = new LazyDataModel<Car>() {
            @Override
            public List<Car> load(int first, int pageSize,
                                  String sortField, SortOrder sortOrder,
                                  Map<String, Object> filters) {
                ${package}.infra.model.SortOrder order = null;
                if (sortOrder != null) {
                    order = sortOrder.equals(SortOrder.ASCENDING) ? ${package}.infra.model.SortOrder.ASCENDING
                            : sortOrder.equals(SortOrder.DESCENDING) ? ${package}.infra.model.SortOrder.DESCENDING
                            : ${package}.infra.model.SortOrder.UNSORTED;
                }
                filter.setFirst(first).setPageSize(pageSize)
                        .setSortField(sortField).setSortOrder(order)
                        .setParams(filters);
                List<Car> list = carService.paginate(filter);
                setRowCount((int) carService.count(filter));
                return list;
            }

            @Override
            public int getRowCount() {
                return super.getRowCount();
            }

            @Override
            public Car getRowData(String key) {
                return carService.findById(new Integer(key));
            }
        };
    }

    public void clear() {
        filter = new Filter<Car>(new Car());
    }

    public List<String> completeModel(String query) {
        List<String> result = carService.getModels(query);
        return result;
    }

    public void findCarById(Integer id) {
        if (id == null) {
            throw new BusinessException("Provide Car ID to load");
        }
        selectedCars.add(carService.findById(id));
    }

    public void delete() {
        int numCars = 0;
        for (Car selectedCar : selectedCars) {
            numCars++;
            carService.remove(selectedCar);
        }
        selectedCars.clear();
        addDetailMessage(numCars + " cars deleted successfully!");
    }

    public List<Car> getSelectedCars() {
        return selectedCars;
    }

    public List<Car> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<Car> filteredValue) {
        this.filteredValue = filteredValue;
    }

    public void setSelectedCars(List<Car> selectedCars) {
        this.selectedCars = selectedCars;
    }

    public LazyDataModel<Car> getCars() {
        return cars;
    }

    public void setCars(LazyDataModel<Car> cars) {
        this.cars = cars;
    }

    public Filter<Car> getFilter() {
        return filter;
    }

    public void setFilter(Filter<Car> filter) {
        this.filter = filter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
