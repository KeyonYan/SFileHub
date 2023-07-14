export const FileSizeFormatter = (bitSize: number) => {
    const unit = ["B", "KB", "MB", "GB", "TB"];
    let index = 0;
    while (bitSize > 1024) {
        bitSize /= 1024;
        index++;
    }
    return `${bitSize.toFixed(2)}${unit[index]}`;
}
