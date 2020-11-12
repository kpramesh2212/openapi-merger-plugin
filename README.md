# openapi-merger-plugin
A plugin to merge OpenAPI-3 specification files into a single file

what does the plugin do
------------------

Consider the following open api v3 schema files color.yaml and book.yaml

color.yaml
```yaml
openapi: 3.0.1
info:
  title: OpenAPI definition
  version: v0
servers:
- url: http://localhost:8080
  description: Generated server url
paths:
  /api/color/{name}:
    get:
      tags:
      - color-controller
      operationId: getColor
      parameters:
      - name: name
        in: path
        required: true
        schema:
          type: string
      responses:
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/Color'
components:
  schemas:
    Color:
      type: object
      properties:
        name:
          type: string
        red:
          type: integer
          format: int32
        green:
          type: integer
          format: int32
        blue:
          type: integer
          format: int32
``` 

book.yaml
```yaml
openapi: 3.0.1
info:
  title: OpenAPI definition
  version: v0
servers:
- url: http://localhost:8080
  description: Generated server url
paths:
  /api/book/{name}:
    get:
      tags:
      - book-controller
      operationId: getBook
      parameters:
      - name: name
        in: path
        required: true
        schema:
          type: string
      responses:
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/Book'
components:
  schemas:
    Book:
      type: object
      properties:
        name:
          type: string
        iban:
          type: string
```

Using the plugin user can merge these files into a single file

merged.yaml
```yaml
openapi: 3.0.1
info:
  title: My title
  version: 1.0.0-SNAPSHOT
servers:
- url: http://localhost:8080
  description: Generated server url
paths:
  /api/book/{name}:
    get:
      tags:
      - book-controller
      operationId: getBook
      parameters:
      - name: name
        in: path
        required: true
        style: simple
        explode: false
        schema:
          type: string
      responses:
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/Book'
  /api/color/{name}:
    get:
      tags:
      - color-controller
      operationId: getColor
      parameters:
      - name: name
        in: path
        required: true
        style: simple
        explode: false
        schema:
          type: string
      responses:
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/Color'
components:
  schemas:
    Book:
      type: object
      properties:
        name:
          type: string
        iban:
          type: string
    Color:
      type: object
      properties:
        name:
          type: string
        red:
          type: integer
          format: int32
        green:
          type: integer
          format: int32
        blue:
          type: integer
          format: int32
``` 
OpenAPI objects
----------------

[Root Objects Open API v3 Specification](https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.3.md#openapi-object)

The plugin will merge the following Root OpenAPI objects and all its sub object from the input files

|S.NO|Field Name| 
|-----|---------|
|1|servers|
|2|paths|
|3|components|
|4|security|
|5|tags|

The plugin will **ignore** following Root OpenAPI objects and all its sub object from the input files.

|S.NO|Field Name| 
|-----|---------|
|1|openapi|
|2|info|
|3|externalDocs|

However the plugin allows the user to configure the above object as input properties that will be placed in the merged file.

Supported File formats
-------------------
1. json
2. yaml 

Plugin inputs
-------------
The plugin expects a directory as input which can contain any number of openapi specification files. Input directory can contain both yaml and json open api specification files, and the plugin can process them all.

Plugin outputs
-------------
The plugin can output the merged file either in json or yaml format, but not both.

 