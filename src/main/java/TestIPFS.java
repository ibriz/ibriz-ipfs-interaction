import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;

import java.io.File;

/**
 * Created by kumarpandey on 8/28/18.
 */
public class TestIPFS {


        public void write() throws Exception {
            IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
            NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File("C:\\Users\\morga\\Pictures\\box1.jpg"));
            MerkleNode addResult = ipfs.add(file).get(0);

            String result = addResult.toJSONString();

            System.out.println("result : " + result);
        }
//        public void read() throws Exception {
//            IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
//            Multihash filePointer = Multihash.fromBase58("QmVt8hQ3jVbkeDSmJNRct4tHmLJozQLVxxC2y5Zs8wvNa2");
//            byte[] fileContents = ipfs.cat(filePointer);
//            System.out.println(new String(fileContents));
//
//        }
//
        public static void main(String[] args) throws Exception {
            TestIPFS obj = new TestIPFS();
//            obj.read();
          obj.write();
        }

}
