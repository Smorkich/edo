package com.education.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

/**
 * @author Nadezhda Pupina
 * Class FilePool отвечает за обращения граждан, расширяет сущность BaseEntity
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@Builder
@Entity
@Table(name = "file_pool")
public class FilePool extends BaseEntity {

    /**
     * storageFileId - file id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "storage_file_id")
    private Long storageFileId;

    /**
     * name - file name
     */
    @NotEmpty (message = "name should not be empty")
    @Column (name = "name")
    @Size(min = 0, max = 50, message = "name should between 0 and 50 characters")
    private String name;

    /**
     * extension - decryption will come later !!
     */
    @NotEmpty (message = "extension should not be empty")
    @Column (name = "extension")
    private String extension;
    /**
     * size - file size
     */
    @NotEmpty (message = "size should not be empty")
    @Size(min = 0, message = "size should from 0 characters")
    @Column (name = "size")
    private Long size;

    /**
     * pageCount - number of pages in file
     */
    @NotEmpty (message = "pageCount should not be empty")
    @Size(min = 0, message = "size should from 0 characters")
    @Column (name = "page_count")
    private Long pageCount;

    /**
     * uploadDate - file upload date
     */
    @NotEmpty (message = "uploadDate should not be empty")
    @Column (name = "upload_date")
    private Date uploadDate;

    /**
     * archivedDate - file archive date
     */
    @Column (name = "archived_date")
    private Date archivedDate;

    /**
     * creator - employee who created the file
     */
    @OneToOne(fetch = FetchType.LAZY)
    @NotEmpty (message = "creator should not be empty")
    @Column (name = "creator")
    private Employee creator;


}