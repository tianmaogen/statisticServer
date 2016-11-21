package cn.ibabygroup.statistic.service;

import cn.ibabygroup.statistic.dto.IDto;
import cn.ibabygroup.statistic.dto.PageDataDto;
import cn.ibabygroup.statistic.model.PageDataModel;
import org.dozer.DozerBeanMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Component
public class PageDataService {
    private static MongoTemplate mongoTemplate;
    private static DozerBeanMapper dozerBeanMapper;
    private static final int START_PAGE = 0;

    /**
     * @param criteria    查询条件
     * @param sort        排序
     * @param currentPage 当前页
     * @param pageSize    每页数量
     * @param domainClass 领域模型类
     * @return 前端页面结构化对象组
     */
    static <T> PageDataModel<T> getPageDataWithSort(Criteria criteria, Sort sort, int currentPage, int pageSize, Class<T> domainClass) {
        Query query = new Query().addCriteria(criteria.and("isDeleted").is(false)).with(sort).skip(currentPage * pageSize).limit(pageSize);
        return PageDataModel.createModel(mongoTemplate.count(query, domainClass), currentPage, pageSize, mongoTemplate.find(query, domainClass));
    }

    static <T> PageDataModel<T> getPageData(Criteria criteria, int currentPage, int pageSize, Class<T> domainClass) {
        Query query = new Query().addCriteria(criteria).skip(currentPage * pageSize).limit(pageSize);
        return PageDataModel.createModel(mongoTemplate.count(query, domainClass), currentPage, pageSize, mongoTemplate.find(query, domainClass));
    }

    public static <T> List<T> getModelsWithCriteria(Criteria criteria, Class<T> targetClazz) {
        return getModelsWithCriteriaBySort(criteria, getSort(0, "weight"), targetClazz);
    }

    public static <T> List<T> getModelsWithCriteriaBySort(Criteria criteria, Sort sort, Class<T> targetClazz) {
        Query query = new Query().addCriteria(criteria.and("isDeleted").is(false)).with(sort);
        return mongoTemplate.find(query, targetClazz);
    }

    public static <T> List<T> getAllWithCriteria(Criteria criteria, Class<T> tClass) {
        Query query = new Query().addCriteria(criteria);
        return mongoTemplate.find(query, tClass);
    }

    /**
     * @param criteria    查询条件
     * @param sort        排序对象
     * @param limit       限制条数
     * @param domainClass 领域模型类
     * @param <T>         领域模型类
     * @return
     */
    static <T> PageDataModel<T> getPageDataInLimitBySort(Criteria criteria, Sort sort, int limit, Class<T> domainClass) {
        return getPageDataWithSort(criteria, sort, START_PAGE, limit, domainClass);

    }

    static <T> T getOneData(Criteria criteria, Sort sort, Class<T> domainClass) {
        PageDataModel<T> model = getPageDataInLimitBySort(criteria, sort, 1, domainClass);
        if (model.getDataList().size() == 0) {
            return null;
        }
        return model.getDataList().get(0);
    }

    static <T> T getOneData(Criteria criteria, Class<T> domainClass) {
        return getModelsWithCriteria(criteria, domainClass).get(0);
    }

    /**
     * @param order   排序（1升序，其他降序）
     * @param sortCol 需要排序的列
     * @return 排序器
     */
    private static Sort getSort(int order, String sortCol) {
        //默认按权重降序
        if (StringUtils.isEmpty(sortCol)) {
            sortCol = "weight";
        }
        Sort.Direction direction = order == 1 ? Sort.Direction.ASC : Sort.Direction.DESC;
        return new Sort(new Sort.Order(direction, sortCol));
    }


    public static void setMongoTemplate(MongoTemplate mongoTemplate) {
        PageDataService.mongoTemplate = mongoTemplate;
    }

    public static <T> PageDataModel<T> getEmptyModel() {
        return PageDataModel.createModel();
    }

    public static <T extends IDto, U> PageDataDto<T> convertToDto(PageDataModel<U> model, Class<T> dtoClass) {
        PageDataDto<T> dto = new PageDataDto<>();
        dozerBeanMapper.map(model, dto);
        try {
            dto.setDataList(batchMappingList(model.getDataList(), dtoClass));
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return dto;
    }

    public static <T, U> List<U> batchMappingList(List<T> from, Class<U> toClazz) throws IllegalAccessException, InstantiationException {
        if (from.size() == 0) {
            return new ArrayList<>();
        }

        Iterator<T> iterator = from.iterator();
        List<U> toList = new ArrayList<>();
        while (iterator.hasNext()) {
            Object ret = toClazz.newInstance();
            dozerBeanMapper.map(iterator.next(), ret);
            toList.add((U) ret);
        }
        return toList;
    }

    public static void setDozerBeanMapper(DozerBeanMapper dozerBeanMapper) {
        PageDataService.dozerBeanMapper = dozerBeanMapper;
    }
}
