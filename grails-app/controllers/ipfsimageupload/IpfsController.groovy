package ipfsimageupload

import io.ipfs.api.IPFS
import io.ipfs.api.MerkleNode
import io.ipfs.api.NamedStreamable
import io.ipfs.multihash.Multihash
import org.springframework.web.multipart.MultipartFile

class IpfsController {

    def showImage(params) {
        IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
        Multihash filePointer = Multihash.fromBase58(params.hash);
        byte[] fileContents = ipfs.cat(filePointer);
        response.contentType = 'image/png' // or the appropriate image content type
        response.outputStream << fileContents
        response.outputStream.flush()
    }

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }

    def uploadImage() {
        def fileByte = request.getFile('image')


        try {
            byte[] bytes = fileByte.getBytes();
            File file = new File("upload\\" + fileByte.getOriginalFilename());

            OutputStream os = new FileOutputStream(file);
            os.write(bytes);
            System.out.println("Write bytes to file.");
            os.close();

            IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
            NamedStreamable.FileWrapper fileWrapper = new NamedStreamable.FileWrapper(file);
            MerkleNode addResult = ipfs.add(fileWrapper).get(0);


            def result = addResult.toJSON();
            print("result: : " + result)

            [ipfsHash: result["Hash"], "name": result["Name"], "size": result["Size"]]
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    def uploadImage1() {
        def file = request.getFile('image')

        String result = ''
        try {
            File fileUpload = multipartToFile(file)
            println fileUpload.getAbsolutePath()
            IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
            println "ipfs = " + file.getOriginalFilename()
//            File fileUploaded =new File("D:\\Company\\iBriz\\DEVELOPMENT\\Grails-React\\IPFS Imageupload\\upload\\" + f.getOriginalFilename())
//            f.transferTo(fileUpload)
            NamedStreamable.FileWrapper fileWrapper = new NamedStreamable.FileWrapper(fileUpload);
            MerkleNode addResult = ipfs.add(fileWrapper).get(0);


            result = addResult.toJSONString();

            System.out.println("result : " + result);

        }
        catch (Exception e) {
            log.error("Your exception message goes here", e)
        }
        return [result: "result"]
    }

    def index() {

//        response.contentType = 'image/png' // or the appropriate image content type
//        response.outputStream << fileContents
//        response.outputStream.flush()
//        return  [file: , contentType: 'image/png']
        [test: "TEST"]
    }
}
