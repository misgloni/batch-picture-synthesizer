import java.io.File;
import java.util.Scanner;

public class ConsoleMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ImageHandler imageHandler = new ImageHandler();
        System.out.println("欢迎使用批量图片合成器小哪吒N号机实验体!!!");
        for (; ; ) {
            System.out.printf("\n\n请输入需要生成的图片文件夹路径: ");
            String path = scanner.nextLine();
            System.out.println("正在检查路径是否合法...");
            File file = new File(path);
            if (file.isDirectory()) {
                System.out.println("路径合法!路径为 " + path);
                System.out.printf("请输入需要生成图片的<<横向排列数量>>: ");
                int x = scanner.nextInt();
                System.out.printf("请输入需要生成图片的<<纵向排列数量>>: ");
                int y = scanner.nextInt();
                System.out.printf("你指定的图片横纵为: 横%d,纵%d\n", x, y);
                String save_path = "";
                System.out.println("是否使用默认生成方式进行生成?(直接生成为 '" + path + File.separator + "temp.png" + "' )\n输入 y/n 确认: ");
                scanner.nextLine();
                String check = scanner.nextLine();
                if (check.equals("y")) {
                    save_path = path + File.separator + "temp.png";
                } else {
                    System.out.println("请指定一个生成路径(现只支持后缀为png): ");
                    save_path = scanner.nextLine();
                }
                System.out.println("开始生成图片!!!请稍候");
                try {
                    imageHandler.createImage(x, y, path, save_path);
                    System.out.println("生成完毕!!!文件为'" + save_path + "'");
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("路径不合法, 请重新检查一次'" + path + "'是否存在!");
            }
        }
    }
}
