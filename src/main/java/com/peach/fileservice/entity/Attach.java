package com.peach.fileservice.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2024/10/9 18:26
 */
@Data
@Table(name = "ATTACH")
public class Attach {

    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 文件名称
     */
    @Column(name = "FILE_NAME")
    private String fileName;

    /**
     * 文件存储路径
     */
    @Column(name = "FILE_PATH")
    private String filePath;

    /**
     * 文件大小
     */
    @Column(name = "FILE_SIZE")
    private double fileSize;

    /**
     * 是否删除
     */
    @Column(name = "IS_DELETED")
    private boolean isDeleted;

    /**
     * 全称
     */
    @Column(name = "ORIGINAL_FILE_NAME")
    private String originalFileName;

    /**
     * 创建时间
     */
    @Column(name = "CREATED_TIME")
    private String createdTime;

    /**
     * 修改时间
     */
    @Column(name = "MODIFIED_TIME")
    private String modifiedTime;

    /**
     * 创建人
     */
    @Column(name = "CREATOR")
    private String creator;

    /**
     * 创建人编码
     */
    @Column(name = "CREATOR_CODE")
    private String creatorCode;

    /**
     * 修改人
     */
    @Column(name = "MODIFIER")
    private String modifier;

    /**
     * 修改人编码
     */
    @Column(name = "MODIFI_CODE")
    private String modifiCode;

    /**
     * 文件类型
     */
    @Column(name = "FILE_TYPE")
    private String fileType;


}
