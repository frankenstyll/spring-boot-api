package com.dsl.repository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.dsl.util.CollectionUtil;
import com.dsl.util.StringUtil;

/**
 * Add Spring JDBCTemplate support
 *
 * @author Administrator
 */
public abstract class BaseRepository {

    private static final Logger logger = LogManager.getLogger(BaseRepository.class);
    private static final String _ROWNUM = "rownum";

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public String decorateRowNumSQL(String sql, int pageIndex, int pageSize) {
//        if (!StringUtils.contains(sql, _ROWNUM)) {
//            thro                       w new IllegalArgumentException("Invalid sql, the sql statement must end with 'rownum r'");
//        }
        if (logger.isInfoEnabled()) {
            logger.info("pageIndex : " + pageIndex);
            logger.info("pageSize : " + pageSize);
        }
        int startRow = getStartRow(pageIndex, pageSize);
        int endRow = getEndRow(pageIndex, pageSize);
        if (logger.isInfoEnabled()) {
            logger.info("startRow : " + startRow);
            logger.info("endRow : " + endRow);
        }
        StringBuilder bf = new StringBuilder();
        bf.append("select aa.* from (");
        bf.append("select a.*, rownum rr from (");
        bf.append(sql);
        bf.append(")a )aa where rr between ").append(startRow).append(" and ").append(endRow);
        return bf.toString();
    }

    public int getStartRow(int pageIndex, int pageSize) {
//        //for excise style(jQuery datatable) : index is full size such as 10, 20, 30
////        return (((pageIndex/pageSize) - 1) * pageSize) + 1;
//        return pageIndex+1;
//        for kcs style : index is id style such as 1, 2, 3
        return ((pageIndex - 1) * pageSize) + 1;
    }

    public int getEndRow(int pageIndex, int pageSize) {
        //for kcs style
        return (pageIndex) * pageSize;
        //for datatable style
//        return pageIndex + pageSize;
    }
    private static final String PRE_COUNT_SQL = "select count(*) from (";
    private static final String SUF_COUNT_SQL = ")";

    public int countTotal(String sql) {
        int count = jdbcTemplate.queryForObject(PRE_COUNT_SQL + sql + SUF_COUNT_SQL, Integer.class);

        if (logger.isDebugEnabled()) {
            logger.debug("count:" + count);
        }
        return count;
    }

    public int countTotal(String sql, Object[] params) {
        int count = 0;
        if (params != null && params.length > 0) {
            count = jdbcTemplate.queryForObject(PRE_COUNT_SQL + sql + SUF_COUNT_SQL, params, Integer.class);
        } else {
            count = countTotal(sql);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("count:" + count);
        }
        return count;
    }

    public int getNextSeq(String seqName) {
        StringBuilder bf = new StringBuilder();
        bf.append("select ");
        bf.append(seqName);
        bf.append(".nextval from dual");
        if (logger.isDebugEnabled()) {
            logger.debug("NextValSql:" + bf.toString());
        }
        int nextVal = jdbcTemplate.queryForObject(bf.toString(), Integer.class);
        if (logger.isDebugEnabled()) {
            logger.debug("NextValResult:" + nextVal);
        }
        return nextVal;
    }

    public int queryForInt(String sql) {
        int intValue = 0;
        if (logger.isInfoEnabled()) {
            logger.info("sql : " + sql.toString());
        }
        intValue = jdbcTemplate.queryForObject(sql.toString(), Integer.class);
        if (logger.isDebugEnabled()) {
            logger.debug("intValue :" + intValue);
        }
        return intValue;
    }

    public int queryForInt(String sql, Object[] params) {
        int intValue = 0;
        if (logger.isInfoEnabled()) {
            logger.info("sql : " + sql.toString());
            logger.info("params : " + StringUtil.toString(params));
        }
        intValue = jdbcTemplate.queryForObject(sql.toString(), params, Integer.class);
        if (logger.isDebugEnabled()) {
            logger.debug("intValue :" + intValue);
        }
        return intValue;
    }

    public String queryForString(String sql) {

        if (logger.isInfoEnabled()) {
            logger.info("sql : " + sql.toString());
        }
        String stringVal = null;
        ArrayList<String> result = (ArrayList) jdbcTemplate.query(sql.toString(), new RowMapper() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString(1);
            }
        });

        if (logger.isInfoEnabled()) {
            logger.info("result size: " + CollectionUtil.getSize(result));
        }
        if (CollectionUtil.isNotEmpty(result)) {
            stringVal = result.get(0);
        }
        return stringVal;
    }

    public String queryForString(String sql, Object[] params) {

        if (logger.isInfoEnabled()) {
            logger.info("sql : " + sql.toString());
            logger.info("params : " + StringUtil.toString(params));
        }
        String stringVal = null;
        ArrayList<String> result = (ArrayList) jdbcTemplate.query(sql.toString(), params, new RowMapper() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString(1);
            }
        });

        if (logger.isInfoEnabled()) {
            logger.info("result size: " + CollectionUtil.getSize(result));
        }
        if (CollectionUtil.isNotEmpty(result)) {
            stringVal = result.get(0);
        }
        return stringVal;
    }

    /**
     * *
     * Get Seq number from PKGEN_TRAN table
     *
     * @param pkKey
     * @return
     */
//    public int getControlNextSeq(String pkKey) {
//        SeqNoGenerator generator = SeqNoGenerator.getInstance();
//        generator.setJdbcTemplate(getJdbcTemplate());
//        int genSeqNo = generator.getSeqNo(pkKey);
//        return genSeqNo;
//    }
    private void printColumnInfo(String table) {

        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(table);

        if (logger.isDebugEnabled()) {
            logger.debug("sql:" + sql.toString());
        }

        List result = jdbcTemplate.query(sql.toString(), new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                Object object = new Object();
                ResultSetMetaData rsm = rs.getMetaData();
                int cnt = rsm.getColumnCount();
                logger.debug("column count:" + cnt);
                for (int i = 1; i <= cnt; i++) {
                    System.out.println(rsm.getColumnName(i) + "= " + rsm.getColumnType(i));
                }
                return object;
            }
        });

        if (logger.isDebugEnabled()) {
            logger.debug("all rows :" + result.size());
        }
    }

    public String getSqlFilterUnit(int ownerUnitId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT OU.UNIT_ID  ");
//        sql.append("FROM ").append(DBConst.UNIT).append(" OU  ");
//        sql.append("WHERE ANCESTOR_UNIT_ID||';' LIKE (SELECT IU.ANCESTOR_UNIT_ID || ';%' FROM ").append(DBConst.UNIT).append(" IU WHERE IU.UNIT_ID = ").append(ownerUnitId).append(" ) ");

        if (logger.isDebugEnabled()) {
            logger.debug("sql:" + sql.toString());
        }
        return sql.toString();
    }
}
