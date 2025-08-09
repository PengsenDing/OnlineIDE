import {File} from './file';

describe('File', () => {
    it('should create an instance', () => {
        const file = new File("test.txt", "documents", "txt")
        expect(file.name).toEqual("test.txt");
        expect(file.path).toEqual("documents");
        expect(file.type).toEqual("txt");
    });
});
