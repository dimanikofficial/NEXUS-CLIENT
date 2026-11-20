package dev.nexus.gui;

import dev.nexus.NexusClient;
import dev.nexus.module.Category;
import dev.nexus.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * NEXUS dark two-column ClickGUI, inspired by the supplied WILD CLIENT reference.
 * Uses a warm orange accent rather than copying its branding.
 */
public final class ClickGuiScreen extends Screen {
    private static final int BG = 0xF00B0A0B;
    private static final int PANEL = 0xF0151416;
    private static final int PANEL_2 = 0xFF1C1A1D;
    private static final int ROW = 0xFF211F22;
    private static final int ROW_HOVER = 0xFF2B2729;
    private static final int ORANGE = 0xFFFF8754;
    private static final int WHITE = 0xFFF4F0F0;
    private static final int MUTED = 0xFF8C8589;

    private Category selected = Category.COMBAT;
    private int panelX, panelY, panelW, panelH;
    private int contentTop;
    private final List<RowHit> rowHits = new ArrayList<>();
    private final List<CategoryHit> categoryHits = new ArrayList<>();

    public ClickGuiScreen() {
        super(Text.literal("NEXUS CLIENT"));
    }

    @Override
    protected void init() {
        panelW = Math.min(820, width - 28);
        panelH = Math.min(590, height - 28);
        panelX = (width - panelW) / 2;
        panelY = (height - panelH) / 2;
        contentTop = panelY + 94;
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx);
        rowHits.clear();
        categoryHits.clear();

        // Main window and header
        ctx.fill(panelX, panelY, panelX + panelW, panelY + panelH, BG);
        ctx.fill(panelX, panelY, panelX + panelW, panelY + 1, 0xFF3A2925);
        ctx.fill(panelX, panelY, panelX + 3, panelY + panelH, ORANGE);
        ctx.fill(panelX + 1, panelY + 1, panelX + panelW - 1, panelY + 62, PANEL);

        // Brand mark and top controls
        ctx.drawText(textRenderer, "N", panelX + 17, panelY + 17, ORANGE, true);
        ctx.drawText(textRenderer, "NEXUS", panelX + 35, panelY + 15, WHITE, true);
        ctx.drawText(textRenderer, "CLIENT", panelX + 35, panelY + 28, MUTED, false);

        int topY = panelY + 12;
        int profileX = panelX + panelW - 340;
        ctx.fill(profileX, topY, profileX + 174, topY + 34, PANEL_2);
        ctx.drawText(textRenderer, "▤  Default config", profileX + 12, topY + 12, 0xFFE4DDDF, false);
        ctx.drawText(textRenderer, "⌄", profileX + 154, topY + 11, MUTED, false);

        int searchX = profileX + 184;
        ctx.fill(searchX, topY, panelX + panelW - 16, topY + 34, PANEL_2);
        ctx.drawText(textRenderer, "⌕  Search modules...", searchX + 12, topY + 12, MUTED, false);

        // Left navigation rail
        int railX = panelX + 1;
        int railTop = panelY + 63;
        ctx.fill(railX, railTop, panelX + 65, panelY + panelH - 1, 0xFF100F11);
        ctx.fill(panelX + 65, railTop, panelX + 66, panelY + panelH - 1, 0xFF292528);

        String[] icons = {"⚔", "⌁", "♙", "◉", "⚙"};
        Category[] categories = Category.values();
        int navY = railTop + 19;
        for (int i = 0; i < categories.length; i++) {
            Category category = categories[i];
            boolean active = category == selected;
            int x1 = panelX + 12;
            int y1 = navY + i * 48;
            if (active) {
                ctx.fill(x1, y1, x1 + 40, y1 + 34, 0xFF30231F);
                ctx.fill(x1, y1, x1 + 2, y1 + 34, ORANGE);
            }
            ctx.drawCenteredTextWithShadow(textRenderer, icons[i], x1 + 20, y1 + 11,
                    active ? ORANGE : MUTED);
            categoryHits.add(new CategoryHit(category, x1, y1, x1 + 40, y1 + 34));
        }

        // Content heading
        int left = panelX + 86;
        ctx.drawText(textRenderer, "Modules  /  " + title(selected), left, panelY + 75, WHITE, true);
        ctx.drawText(textRenderer, "Manage your active features", left, panelY + 87, MUTED, false);

        // Two-column module list
        int colGap = 14;
        int innerW = panelW - 86 - 18;
        int colW = (innerW - colGap) / 2;
        int rowH = 42;
        int gapY = 8;
        List<Module> modules = NexusClient.MODULES.in(selected);
        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            int col = i % 2;
            int row = i / 2;
            int x = left + col * (colW + colGap);
            int y = contentTop + row * (rowH + gapY);
            if (y + rowH > panelY + panelH - 36) continue;

            boolean hover = mouseX >= x && mouseX <= x + colW && mouseY >= y && mouseY <= y + rowH;
            ctx.fill(x, y, x + colW, y + rowH, hover ? ROW_HOVER : ROW);
            ctx.fill(x, y, x + colW, y + 1, 0xFF302C30);
            ctx.drawText(textRenderer, module.getName(), x + 14, y + 9, WHITE, false);
            ctx.drawText(textRenderer, module.getDescription(), x + 14, y + 23, MUTED, false);

            int sx = x + colW - 43;
            int sy = y + 14;
            ctx.fill(sx, sy, sx + 29, sy + 14, module.isEnabled() ? 0xFFFF8754 : 0xFF514B50);
            int knobX = module.isEnabled() ? sx + 17 : sx + 3;
            ctx.fill(knobX, sy + 3, knobX + 8, sy + 11, 0xFFF8F5F5);
            rowHits.add(new RowHit(module, x, y, x + colW, y + rowH));
        }

        // Footer
        ctx.fill(panelX + 66, panelY + panelH - 29, panelX + panelW, panelY + panelH, PANEL);
        ctx.drawText(textRenderer, "NEXUS CLIENT  •  1.21.x", left, panelY + panelH - 18, MUTED, false);
        ctx.drawText(textRenderer, "RIGHT SHIFT  •  CLOSE", panelX + panelW - 145,
                panelY + panelH - 18, 0xFFB6AEB2, false);
        super.render(ctx, mouseX, mouseY, delta);
    }

    private String title(Category category) {
        String s = category.name().toLowerCase();
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);
        for (CategoryHit hit : categoryHits) {
            if (hit.contains(mouseX, mouseY)) {
                selected = hit.category();
                return true;
            }
        }
        for (RowHit hit : rowHits) {
            if (hit.contains(mouseX, mouseY)) {
                hit.module().toggle();
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private record RowHit(Module module, int x1, int y1, int x2, int y2) {
        boolean contains(double x, double y) { return x >= x1 && x <= x2 && y >= y1 && y <= y2; }
    }
    private record CategoryHit(Category category, int x1, int y1, int x2, int y2) {
        boolean contains(double x, double y) { return x >= x1 && x <= x2 && y >= y1 && y <= y2; }
    }
}
