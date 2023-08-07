# Attachment PDF/A-3
## Prerequisites
- Java JDK 8 
- Eclipse (Editor สำหรับใช้การพัฒนา)

## Maven Dependencies
| **#** | **GroupId** | **ArtifactId** | **Version**|
| ------ | ------ | ------ | ------ |
| 1 | org.apache.pdfbox | pdfbox | 2.0.22 |
| 2 | org.apache.pdfbox | xmpbox | 2.0.22 |
| 3 | org.apache.pdfbox | preflight | 2.0.22 |
| 4 | org.apache.pdfbox | jempbox | 1.8.16 |
| 5 | commons-io | commons-io | 2.5 |
| 6 | com.googlecode.json-simple | json-simple | 1.1.1 |
| 7 | javax.activation | activation | 1.1.1 |
| 8 | javax.xml.bind | jaxb-api | 2.4.0-b180830.0359 |


## Getting started

**Input**

- PDF File (ไฟล์ PDF หลักสำหรับแนบเอกสาร)
- File Attachment (ไฟล์ที่จะนำมาแนบใน PDF สามารถแนบได้หลายไฟล์)

**Output**

- PDF File (ไฟล์ PDF ที่มีการแนบเอกสารแล้ว  โดยจะอยู่ที่เดียวกับไฟล์ต้นฉบับ แต่มีชื่อ Attachment_ ต่อหน้าไฟล์ตั้งต้น เช่น ต้นฉบับ test.pdf หลังแนบจะเป็น  Attachment_test.pdf)